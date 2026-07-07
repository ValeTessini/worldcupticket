package com.worldcupticket.ms_notifications.service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@Service
public class NotificationService {

    public byte[] generateQr(String payload) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(payload, BarcodeFormat.QR_CODE, 300, 300);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
                return baos.toByteArray();
            }
        } catch (WriterException | java.io.IOException ex) {
            throw new IllegalStateException("Error generando QR", ex);
        }
    }

    public void sendMail(String destination, String subject, String body, byte[] qrImage) {
        String message = new String(qrImage, StandardCharsets.ISO_8859_1);
        System.out.println("[ms-notifications] Simulating mail send to " + destination);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("QR image bytes: " + message.length() + " bytes");
    }
}
