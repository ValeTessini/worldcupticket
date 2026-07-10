import { Box, Button, Card, CardContent, Chip, Grid, TextField, Typography } from '@mui/material';
import { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { matches } from '../data/matches';

export default function HomePage() {
    const [search, setSearch] = useState('');
    const navigate = useNavigate();

    const filteredMatches = useMemo(
        () =>
            matches.filter((match) => {
                const query = search.toLowerCase();
                return [match.home, match.away, match.group, match.stadium].some((field) =>
                    field.toLowerCase().includes(query)
                );
            }),
        [search]
    );

    return (
        <div className="space-y-8">
            <Box className="rounded-[28px] border border-brand-800 bg-brand-800/80 p-10 shadow-soft">
                <div className="flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">
                    <div className="max-w-2xl space-y-4">
                        <Typography variant="overline" className="text-brand-400">FIFA World Cup · 11 jun – 19 jul 2026</Typography>
                        <Typography variant="h2" className="text-5xl font-black leading-tight text-white">
                            Conseguí tus entradas al <span className="text-brand-400">Mundial 2026</span>
                        </Typography>
                        <Typography className="max-w-xl text-brand-300">
                            104 partidos, 16 sedes, 3 países. Reservá tu lugar antes de que se agote.
                        </Typography>
                    </div>
                    <div className="grid gap-4 sm:grid-cols-3">
                        <div>
                            <Typography className="text-4xl font-black text-white">104</Typography>
                            <Typography className="text-sm uppercase text-brand-400">Partidos</Typography>
                        </div>
                        <div>
                            <Typography className="text-4xl font-black text-white">16</Typography>
                            <Typography className="text-sm uppercase text-brand-400">Sedes</Typography>
                        </div>
                        <div>
                            <Typography className="text-4xl font-black text-white">48</Typography>
                            <Typography className="text-sm uppercase text-brand-400">Selecciones</Typography>
                        </div>
                    </div>
                </div>
            </Box>

            <Card className="rounded-[24px] border border-brand-800 bg-brand-800/80 p-4 shadow-soft">
                <CardContent className="flex flex-col gap-3 sm:flex-row sm:items-center">
                    <TextField
                        fullWidth
                        placeholder="Buscar por equipo, ciudad o fecha…"
                        variant="filled"
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        InputProps={{
                            className: 'bg-brand-900 text-white',
                        }}
                    />
                    <Button variant="contained" className="bg-brand-400 text-brand-950 hover:bg-brand-300">
                        Buscar
                    </Button>
                </CardContent>
            </Card>

            <Grid container spacing={4}>
                {filteredMatches.map((match) => (
                    <Grid item xs={12} md={6} lg={4} key={match.id}>
                        <Card className="rounded-[24px] border border-brand-800 bg-brand-800/80 shadow-soft transition hover:-translate-y-1">
                            <CardContent className="space-y-4">
                                <div className="flex items-center justify-between gap-3">
                                    <Chip
                                        label={match.status}
                                        className={`font-semibold ${match.status === 'DISPONIBLE'
                                                ? 'bg-brand-400 text-brand-950'
                                                : match.status === 'QUEDAN POCAS'
                                                    ? 'bg-amber-500/20 text-amber-300'
                                                    : 'bg-red-500/20 text-red-300'
                                            } rounded-full py-1 px-3 text-xs uppercase`}
                                    />
                                    <Typography variant="body2" className="text-brand-300">
                                        {match.date}
                                    </Typography>
                                </div>
                                <div className="space-y-3">
                                    <div className="flex items-center justify-between gap-4">
                                        <div className="text-center">
                                            <div className="text-3xl">{match.flagHome}</div>
                                            <Typography className="text-sm uppercase text-brand-300">{match.home}</Typography>
                                        </div>
                                        <Typography className="text-2xl font-black text-brand-400">VS</Typography>
                                        <div className="text-center">
                                            <div className="text-3xl">{match.flagAway}</div>
                                            <Typography className="text-sm uppercase text-brand-300">{match.away}</Typography>
                                        </div>
                                    </div>
                                    <div className="grid gap-2 rounded-3xl border border-brand-800 bg-brand-900/70 p-4 text-sm text-brand-300">
                                        <div className="flex items-center gap-2">
                                            <span className="inline-flex h-7 w-7 items-center justify-center rounded-xl border border-brand-700">🗓</span>
                                            {match.dateTime}
                                        </div>
                                        <div className="flex items-center gap-2">
                                            <span className="inline-flex h-7 w-7 items-center justify-center rounded-xl border border-brand-700">📍</span>
                                            {match.stadium}
                                        </div>
                                    </div>
                                </div>
                                <div className="flex items-center justify-between gap-4">
                                    <div>
                                        <Typography variant="caption" className="text-brand-400 uppercase">Desde</Typography>
                                        <Typography className="text-2xl font-black text-brand-400">{match.price}</Typography>
                                    </div>
                                    <Button
                                        variant="contained"
                                        className="bg-brand-400 text-brand-950 hover:bg-brand-300"
                                        onClick={() => navigate(`/match/${match.id}`)}
                                    >
                                        Ver detalles →
                                    </Button>
                                </div>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
}
