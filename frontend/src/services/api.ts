import {ConversionResult, Currency} from '../types';

const API_BASE_URL = 'http://localhost:8080';

export const fetchSupportedCurrencies = async (): Promise<Currency[]> => {
    try {
        const response = await fetch(`${API_BASE_URL}/currencies`);
        if (!response.ok) {
            throw new Error('Failed to fetch currencies');
        }
        return response.json();
    } catch (error) {
        console.error('Error fetching currencies:', error);
        throw error;
    }
};

export const convertCurrency = async (
    fromCurrency: string,
    toCurrency: string,
    amount: number
): Promise<ConversionResult> => {
    try {
        const response = await fetch(`${API_BASE_URL}/conversions/${fromCurrency}/${toCurrency}?amount=${amount}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-Token': document.querySelector('meta[name="csrf-token"]')?.getAttribute('content') || '',
            }
        });

        if (!response.ok) {
            if (response.status === 400) {
                throw new Error('Currency not supported');
            } else {
                throw new Error(await response.text() || 'Conversion failed');
            }
        }

        return response.json();
    } catch (error) {
        console.error('Error converting currency:', error);
        throw error;
    }
};