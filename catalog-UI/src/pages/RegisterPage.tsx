import { useState } from 'react';
import { Button, Card, CardContent, Grid, TextField, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function RegisterPage() {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    return (
        <div className="mx-auto max-w-2xl rounded-[32px] border border-brand-800 bg-brand-800/80 p-8 shadow-soft">
            <Typography variant="h4" className="mb-2 font-black text-white">
                Registrarse
            </Typography>
            <Typography className="mb-8 text-brand-300">Crea tu cuenta de usuario para acceder a tus entradas y reservas.</Typography>
            <Card className="rounded-[28px] border border-brand-800 bg-brand-900/90 p-6">
                <CardContent className="space-y-5">
                    <Grid container spacing={3}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                fullWidth
                                label="Nombre"
                                variant="filled"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                InputProps={{ className: 'bg-brand-950 text-white' }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                fullWidth
                                label="Apellido"
                                variant="filled"
                                value={surname}
                                onChange={(e) => setSurname(e.target.value)}
                                InputProps={{ className: 'bg-brand-950 text-white' }}
                            />
                        </Grid>
                    </Grid>
                    <TextField
                        fullWidth
                        label="Email"
                        variant="filled"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        InputProps={{ className: 'bg-brand-950 text-white' }}
                    />
                    <Grid container spacing={3}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                fullWidth
                                type="password"
                                label="Contraseña"
                                variant="filled"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                InputProps={{ className: 'bg-brand-950 text-white' }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                fullWidth
                                type="password"
                                label="Confirmar contraseña"
                                variant="filled"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                InputProps={{ className: 'bg-brand-950 text-white' }}
                            />
                        </Grid>
                    </Grid>
                    <div className="flex items-start gap-3 text-sm text-brand-400">
                        <input type="checkbox" id="terms" className="mt-1 h-4 w-4 rounded border-brand-600 bg-brand-900 text-brand-400" />
                        <label htmlFor="terms">Acepto los términos y condiciones y la política de privacidad de WorldCupTicket.</label>
                    </div>
                    <Button
                        variant="contained"
                        className="bg-brand-400 text-brand-950 hover:bg-brand-300 w-full"
                        onClick={() => navigate('/')}
                    >
                        Crear cuenta
                    </Button>
                </CardContent>
            </Card>
        </div>
    );
}
