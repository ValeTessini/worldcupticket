package com.worldcupticket.ms_sales.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.worldcupticket.ms_sales.dto.OrderItemResponseDTO;
import com.worldcupticket.ms_sales.dto.OrderResponseDTO;
import com.worldcupticket.ms_sales.dto.PaymentResponseDTO;
import com.worldcupticket.ms_sales.service.OrderService;
import com.worldcupticket.ms_sales.service.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final OrderService orderService;

	@Override
	public PaymentResponseDTO createPaymentPreference(OrderResponseDTO order) throws MPException, MPApiException {

	    List<PreferenceItemRequest> items = order.items().stream()
					.map(this::toPreferenceItemRequest)
					.toList();

		//Aca va la URL del sitio web al que redirigir al usuario despues de realizar el pago
		PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
				.success("https://www.example.com/success")
				.failure("https://www.example.com/failure")
				.pending("https://www.example.com/pending")
				.build();

		//Aca va la URL a la que MercadoPago va a mandar el webhook cuando el pago se procese
		PreferenceRequest preferenceRequest = PreferenceRequest.builder()
				.items(items)
				.backUrls(backUrls)
				.externalReference(order.id().toString())
				.notificationUrl("https://www.example.com/notifications")
				.build();

		Preference preference = new PreferenceClient().create(preferenceRequest);
		return new PaymentResponseDTO(preference.getInitPoint());
	}

	private PreferenceItemRequest toPreferenceItemRequest(OrderItemResponseDTO item) {
		return PreferenceItemRequest.builder()
				.id(item.sectorId())
				.title("Sector " + item.sectorId())
				.quantity(item.quantity())
				.unitPrice(item.unitPrice())
				.build();
	}

	@Override
	public void processMercadoPagoWebHook(Map<String, Object> payload) throws MPException, MPApiException {
	    String type = (String) payload.get("type");

	    if (!"payment".equals(type)) {
	        return;
	    }

		Map<String, Object> data = (Map<String, Object>) payload.get("data");
		Long paymentId = Long.parseLong((String) data.get("id"));

		PaymentClient paymentClient = new PaymentClient();
		Payment payment = paymentClient.get(paymentId);
		Long orderId = Long.parseLong(payment.getExternalReference());
		String status = payment.getStatus(); // "approved", "rejected", "pending", etc.

		if ("approved".equals(status)) {
			orderService.completeOrder(orderId);
		}
		else if ("rejected".equals(status)) {
			orderService.cancelOrder(orderId);
		}
	}

}
