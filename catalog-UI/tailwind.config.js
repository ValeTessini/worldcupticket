import type { Config } from 'tailwindcss';

export default {
    content: ['./index.html', './src/**/*.{ts,tsx}'],
    theme: {
        extend: {
            colors: {
                brand: {
                    950: '#090c12',
                    900: '#0a0e14',
                    800: '#121823',
                    700: '#1a2230',
                    600: '#26303f',
                    500: '#5884cf',
                    400: '#f5c451',
                    300: '#38d39f',
                    200: '#e2563f',
                },
            },
            boxShadow: {
                soft: '0 20px 50px rgba(0,0,0,.25)',
            },
        },
    },
    plugins: [],
} satisfies Config;
