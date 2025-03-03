import {useEffect, useState} from 'react';
import {Currency} from '../types';
import {fetchSupportedCurrencies} from '../services/api';

export const useCurrencies = () => {
    const [currencies, setCurrencies] = useState<Currency[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const loadCurrencies = async () => {
            try {
                setLoading(true);
                const data = await fetchSupportedCurrencies();
                setCurrencies(data);
                setError(null);
            } catch (err) {
                setError('Failed to load currencies. Please try again later.');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        loadCurrencies();
    }, []);

    return {currencies, loading, error};
};