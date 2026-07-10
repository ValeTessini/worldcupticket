import { useState } from 'react';
import { Button, Card, CardContent, TextField, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    return (
        <div className="mx-auto max-w-xl rounded-[32px] border border-brand-800 bg-brand-800/80 p-8 shadow-soft">
            <Typography variant="h4" className="mb-2 font-black text-white">
                Iniciar sesión
            </Typography>
            <Typography className="mb-8 text-brand-300">Ingresá a tu cuenta para comprar entradas y ver tus órdenes.</Typography>
            <Card className="rounded-[28px] border border-brand-800 bg-brand-900/90 p-6">
                <CardContent className="space-y-5">
                    <TextField
                        fullWidth
                        label="Email"
                        variant="filled"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        InputProps={{ className: 'bg-brand-950 text-white' }}
                    />
                    <TextField
                        fullWidth
                        type="password"
                        label="Contraseña"
                        variant="filled"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        InputProps={{ className: 'bg-brand-950 text-white' }}
                    />
                    <div className="flex items-center justify-between text-sm text-brand-400">
                        <button className="text-brand-300 hover:text-white">¿Olvidaste tu contraseña?</button>
                        <Typography> </Typography>
                    </div>
                    <Button
                        variant="contained"
                        className="bg-brand-400 text-brand-950 hover:bg-brand-300 w-full"
                        onClick={() => navigate('/')}
                    >
                        Ingresar
                    </Button>
                    <Button
                        variant="outlined"
                        className="border-brand-600 text-brand-300 hover:border-brand-400 hover:text-white w-full"
                    >
                        Continuar con Google
                    </Button>
                </CardContent>
            </Card>
        </div>
    );
}
