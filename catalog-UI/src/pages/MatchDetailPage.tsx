import { useParams } from 'react-router-dom';
import { Box, Button, Card, CardContent, Chip, Grid, Typography } from '@mui/material';
import { matches } from '../data/matches';

export default function MatchDetailPage() {
    const { id } = useParams();
    const match = matches.find((item) => item.id === id) ?? matches[0];

    return (
        <div className="space-y-8">
            <div className="flex flex-col gap-4 rounded-[28px] border border-brand-800 bg-brand-800/80 p-8 shadow-soft lg:flex-row lg:items-center lg:justify-between">
                <div className="space-y-3">
                    <Typography variant="overline" className="text-brand-400">Detalle del partido</Typography>
                    <Typography variant="h3" className="text-4xl font-black text-white">
                        {match.home} vs {match.away}
                    </Typography>
                    <Typography className="max-w-2xl text-brand-300">{match.summary}</Typography>
                </div>
                <Chip label={match.status} className="rounded-full bg-brand-400 px-4 py-2 text-sm font-semibold text-brand-950" />
            </div>

            <Grid container spacing={4}>
                <Grid item xs={12} lg={7}>
                    <Card className="rounded-[28px] border border-brand-800 bg-brand-800/80 shadow-soft">
                        <CardContent className="space-y-5 p-8">
                            <div className="grid gap-4 sm:grid-cols-2">
                                <div className="rounded-3xl border border-brand-800 bg-brand-900/80 p-5">
                                    <Typography variant="overline" className="text-brand-400">Fecha</Typography>
                                    <Typography className="mt-2 text-lg font-semibold text-white">{match.dateTime}</Typography>
                                </div>
                                <div className="rounded-3xl border border-brand-800 bg-brand-900/80 p-5">
                                    <Typography variant="overline" className="text-brand-400">Estadio</Typography>
                                    <Typography className="mt-2 text-lg font-semibold text-white">{match.stadium}</Typography>
                                </div>
                            </div>
                            <div className="rounded-[28px] border border-brand-800 bg-brand-900/80 p-6">
                                <Typography variant="h6" className="mb-4 text-white">Sectores disponibles</Typography>
                                <div className="grid gap-3">
                                    {match.sectors.map((sector) => (
                                        <div key={sector.name} className="flex items-center justify-between rounded-3xl border border-brand-800 bg-brand-950/90 p-4">
                                            <div>
                                                <div className="flex items-center gap-2 text-sm uppercase tracking-[0.18em] text-brand-400">
                                                    {sector.type}
                                                    {sector.vip ? 'VIP' : ''}
                                                </div>
                                                <div className="mt-1 font-semibold text-white">{sector.name}</div>
                                                <div className="text-sm text-brand-400">{sector.available} disponibles</div>
                                            </div>
                                            <div className="text-right">
                                                <div className="text-2xl font-black text-brand-400">{sector.price}</div>
                                                <Button variant="outlined" className="mt-2 rounded-full border-brand-600 text-brand-300 hover:border-brand-400 hover:text-white" size="small">
                                                    Seleccionar
                                                </Button>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} lg={5}>
                    <Card className="rounded-[28px] border border-brand-800 bg-brand-800/80 shadow-soft">
                        <CardContent className="space-y-5 p-8">
                            <Typography variant="h6" className="text-white">Mapa del estadio</Typography>
                            <Typography className="text-sm text-brand-400">Tocá un sector para ver disponibilidad y precio.</Typography>
                            <div className="overflow-hidden rounded-[24px] border border-brand-800 bg-brand-950 p-6">
                                <div className="relative aspect-[4/3] bg-gradient-to-br from-brand-900 via-brand-800 to-brand-950">
                                    <div className="absolute inset-0 grid grid-cols-2 grid-rows-2 gap-3 p-4">
                                        {match.sectors.slice(0, 4).map((sector) => (
                                            <div
                                                key={sector.name}
                                                className="rounded-3xl border border-brand-800 bg-brand-900/90 p-3 text-sm text-brand-200"
                                            >
                                                <div className="font-semibold text-white">{sector.name}</div>
                                                <div className="text-brand-400">{sector.available} disponibles</div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>
                            <Button variant="contained" className="bg-brand-400 text-brand-950 hover:bg-brand-300 w-full">
                                Reservar entradas
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
        </div>
    );
}
