import { Outlet, NavLink, useNavigate } from 'react-router-dom';
import { Button } from '@mui/material';

const navItems = [
    { label: 'Partidos', to: '/' },
    { label: 'Mis Entradas', to: '/account' },
    { label: 'Admin', to: '/admin' },
];

export default function Layout() {
    const navigate = useNavigate();

    return (
        <div className="min-h-screen bg-brand-900 text-white">
            <header className="sticky top-0 z-50 border-b border-brand-800 bg-brand-900/95 backdrop-blur-xl">
                <div className="mx-auto flex max-w-7xl items-center gap-8 px-6 py-4">
                    <div className="flex items-center gap-3 font-display text-2xl uppercase tracking-[0.25em] text-white">
                        <span className="inline-flex h-3 w-3 rounded-full bg-brand-400" />
                        WorldCupTicket
                    </div>
                    <nav className="flex flex-1 items-center gap-3">
                        {navItems.map((item) => (
                            <NavLink
                                key={item.to}
                                to={item.to}
                                className={({ isActive }) =>
                                    `rounded-xl px-3 py-2 text-sm font-semibold transition ${isActive ? 'bg-brand-400 text-brand-950' : 'text-brand-300 hover:text-white'
                                    }`
                                }
                            >
                                {item.label}
                            </NavLink>
                        ))}
                    </nav>
                    <div className="flex items-center gap-3">
                        <Button
                            variant="contained"
                            color="secondary"
                            className="bg-brand-400 text-brand-950 hover:bg-brand-300"
                            onClick={() => navigate('/login')}
                        >
                            Iniciar sesión
                        </Button>
                        <Button
                            variant="outlined"
                            className="border-brand-600 text-brand-300 hover:border-brand-400 hover:text-white"
                            onClick={() => navigate('/register')}
                        >
                            Registrarse
                        </Button>
                    </div>
                </div>
            </header>

            <main className="mx-auto max-w-7xl px-6 py-8">
                <Outlet />
            </main>
        </div>
    );
}
