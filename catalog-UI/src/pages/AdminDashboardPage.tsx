import { Box, Button, Card, CardContent, Grid, Typography } from '@mui/material';
import { adminStats, adminOrders } from '../data/admin';

export default function AdminDashboardPage() {
    return (
        <div className="space-y-8">
            <div className="rounded-[28px] border border-brand-800 bg-brand-800/80 p-8 shadow-soft">
                <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
                    <div>
                        <Typography variant="overline" className="text-brand-400">Panel admin</Typography>
                        <Typography variant="h3" className="text-4xl font-black text-white">Reportes y gestión del catálogo</Typography>
                    </div>
                    <Button variant="contained" className="bg-brand-400 text-brand-950 hover:bg-brand-300">
                        Nueva pista de partido
                    </Button>
                </div>
            </div>

            <Grid container spacing={4}>
                {adminStats.map((stat) => (
                    <Grid item xs={12} sm={6} md={3} key={stat.label}>
                        <Card className="rounded-[24px] border border-brand-800 bg-brand-900/90 p-6 shadow-soft">
                            <CardContent>
                                <Typography className="text-sm uppercase tracking-[0.18em] text-brand-400">{stat.label}</Typography>
                                <Typography className="mt-4 text-4xl font-black text-white">{stat.value}</Typography>
                                <Typography className="mt-2 text-sm text-brand-300">{stat.detail}</Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <div className="rounded-[28px] border border-brand-800 bg-brand-800/80 p-6 shadow-soft">
                <div className="mb-6 flex items-center justify-between">
                    <div>
                        <Typography variant="h6" className="text-white">Últimas órdenes</Typography>
                        <Typography className="text-sm text-brand-400">Estado, monto y seguimiento de ventas recientes.</Typography>
                    </div>
                    <Button variant="contained" className="bg-brand-400 text-brand-950 hover:bg-brand-300">
                        Ver todas
                    </Button>
                </div>
                <div className="overflow-x-auto">
                    <table className="min-w-full border-separate border-spacing-0 text-sm text-brand-300">
                        <thead>
                            <tr className="border-b border-brand-800 text-left text-xs uppercase tracking-[0.16em] text-brand-500">
                                <th className="px-4 py-3">ID</th>
                                <th className="px-4 py-3">Comprador</th>
                                <th className="px-4 py-3">Partido</th>
                                <th className="px-4 py-3">Sector</th>
                                <th className="px-4 py-3 text-right">Monto</th>
                                <th className="px-4 py-3">Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            {adminOrders.map((order) => (
                                <tr key={order.id} className="border-b border-brand-800 last:border-none">
                                    <td className="px-4 py-4 text-brand-300">{order.id}</td>
                                    <td className="px-4 py-4">{order.customer}</td>
                                    <td className="px-4 py-4">{order.match}</td>
                                    <td className="px-4 py-4">{order.sector}</td>
                                    <td className="px-4 py-4 text-right text-brand-400">{order.amount}</td>
                                    <td className="px-4 py-4">
                                        <span className={`rounded-full px-3 py-1 text-xs font-semibold ${order.status === 'CONFIRMADA'
                                                ? 'bg-brand-400 text-brand-950'
                                                : order.status === 'RESERVADA'
                                                    ? 'bg-amber-500/20 text-amber-200'
                                                    : 'bg-brand-800 text-brand-300'
                                            }`}>
                                            {order.status}
                                        </span>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}
