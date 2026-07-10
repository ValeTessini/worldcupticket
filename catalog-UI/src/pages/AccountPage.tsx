import { useState } from 'react';
import { Box, Button, Card, CardContent, Grid, Typography } from '@mui/material';
import { orders } from '../data/orders';

export default function AccountPage() {
    const [activeTab, setActiveTab] = useState<'upcoming' | 'history'>('upcoming');

    return (
        <div className="grid gap-8 lg:grid-cols-[260px_1fr]">
            <aside className="rounded-[28px] border border-brand-800 bg-brand-800/80 p-6 shadow-soft">
                <div className="flex items-center gap-4 border-b border-brand-800 pb-6">
                    <div className="flex h-14 w-14 items-center justify-center rounded-full bg-gradient-to-br from-brand-400 to-brand-500 text-brand-950 font-black">
                        JM
                    </div>
                    <div>
                        <Typography className="font-semibold text-white">Juan Martínez</Typography>
                        <Typography className="text-sm text-brand-400">juan.martinez@gmail.com</Typography>
                    </div>
                </div>
                <div className="mt-6 space-y-2">
                    <button
                        className={`w-full rounded-3xl px-4 py-3 text-left text-sm font-semibold transition ${activeTab === 'upcoming' ? 'bg-brand-900 text-white' : 'text-brand-300 hover:bg-brand-900'
                            }`}
                        onClick={() => setActiveTab('upcoming')}
                    >
                        Próximos partidos
                    </button>
                    <button
                        className={`w-full rounded-3xl px-4 py-3 text-left text-sm font-semibold transition ${activeTab === 'history' ? 'bg-brand-900 text-white' : 'text-brand-300 hover:bg-brand-900'
                            }`}
                        onClick={() => setActiveTab('history')}
                    >
                        Pasados
                    </button>
                    <button className="w-full rounded-3xl px-4 py-3 text-left text-sm font-semibold text-brand-300 hover:bg-brand-900">
                        Configuración
                    </button>
                    <button className="w-full rounded-3xl px-4 py-3 text-left text-sm font-semibold text-red-400 hover:bg-brand-900">
                        Cerrar sesión
                    </button>
                </div>
            </aside>

            <div className="space-y-6">
                <div className="flex flex-col gap-4 rounded-[28px] border border-brand-800 bg-brand-800/80 p-8 shadow-soft lg:flex-row lg:items-center lg:justify-between">
                    <div>
                        <Typography variant="h4" className="font-black text-white">
                            Mis Entradas
                        </Typography>
                        <Typography className="text-brand-400">Donde ver y descargar tus órdenes confirmadas o reservadas.</Typography>
                    </div>
                    <div className="inline-flex items-center gap-3 rounded-full bg-brand-900/80 px-4 py-3 text-sm text-brand-300">
                        <span className="rounded-full bg-brand-400 px-3 py-1 text-brand-950">3</span>
                        Entradas activas
                    </div>
                </div>

                <div className="rounded-[28px] border border-brand-800 bg-brand-800/80 p-6 shadow-soft">
                    <div className="mb-6 flex items-center gap-3">
                        <button
                            className={`rounded-3xl px-5 py-3 text-sm font-semibold uppercase tracking-[0.12em] ${activeTab === 'upcoming' ? 'bg-brand-400 text-brand-950' : 'text-brand-300 hover:bg-brand-900'
                                }`}
                            onClick={() => setActiveTab('upcoming')}
                        >
                            Próximos partidos
                        </button>
                        <button
                            className={`rounded-3xl px-5 py-3 text-sm font-semibold uppercase tracking-[0.12em] ${activeTab === 'history' ? 'bg-brand-400 text-brand-950' : 'text-brand-300 hover:bg-brand-900'
                                }`}
                            onClick={() => setActiveTab('history')}
                        >
                            Pasados
                        </button>
                    </div>
                    <div className="space-y-4">
                        {orders
                            .filter((order) => (activeTab === 'upcoming' ? order.status !== 'EXPIRADA' : order.status === 'EXPIRADA'))
                            .map((order) => (
                                <Card key={order.id} className="rounded-[24px] border border-brand-800 bg-brand-900/90 p-4">
                                    <CardContent className="grid gap-4 sm:grid-cols-[auto_1fr_auto] items-center">
                                        <div className="flex items-center gap-4">
                                            <div className={`h-16 w-16 rounded-3xl ${order.status === 'CONFIRMADA' ? 'bg-brand-500' : 'bg-brand-800'}`}>
                                                <div className="grid h-full place-items-center text-lg font-black text-white">{order.initials}</div>
                                            </div>
                                            <div>
                                                <Typography className="font-semibold text-white">{order.match}</Typography>
                                                <Typography className="text-sm text-brand-400">{order.detail}</Typography>
                                            </div>
                                        </div>
                                        <div className="text-sm text-brand-300">
                                            <div>{order.date}</div>
                                            <div>{order.sector}</div>
                                        </div>
                                        <div className="flex flex-col items-end gap-2">
                                            <span className={`rounded-full px-3 py-1 text-xs font-bold uppercase ${order.status === 'CONFIRMADA'
                                                    ? 'bg-brand-400 text-brand-950'
                                                    : order.status === 'RESERVADA'
                                                        ? 'bg-amber-500/20 text-amber-200'
                                                        : 'bg-brand-800 text-brand-300'
                                                }`}
                                            >
                                                {order.status}
                                            </span>
                                            <Button variant="outlined" className="rounded-full border-brand-600 text-brand-300 hover:border-brand-400 hover:text-white text-xs">
                                                Ver QR
                                            </Button>
                                        </div>
                                    </CardContent>
                                </Card>
                            ))}
                    </div>
                </div>
            </div>
        </div>
    );
}
