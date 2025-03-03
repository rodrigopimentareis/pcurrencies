import React, {useCallback, useState} from 'react';
import {useTranslation} from 'react-i18next';
import CurrencySelect from './CurrencySelect';
import ConversionResultDisplay from './ConversionResult';
import AmountInput from './common/AmountInput';
import SwapButton from './common/SwapButton';
import ErrorMessage from './common/ErrorMessage';
import {useCurrencies} from '../hooks/useCurrencies';
import {convertCurrency} from '../services/api';
import {ConversionResult} from '../types';
import Button from './common/Button';

const CurrencyConverter: React.FC = () => {
    const {t} = useTranslation();
    const {currencies, loading, error} = useCurrencies();
    const [fromCurrency, setFromCurrency] = useState<string>('EUR');
    const [toCurrency, setToCurrency] = useState<string>('BRL');
    const [amount, setAmount] = useState<string>('');
    const [isConverting, setIsConverting] = useState<boolean>(false);
    const [conversionError, setConversionError] = useState<string | null>(null);
    const [result, setResult] = useState<ConversionResult | null>(null);

    const handleSubmit = useCallback(
        async (e: React.FormEvent) => {
            e.preventDefault();

            const amountValue = parseFloat(amount);
            if (!fromCurrency || !toCurrency || !amount || isNaN(amountValue) || amountValue <= 0) {
                setConversionError(!fromCurrency || !toCurrency ? t('selectCurrencies') : t('invalidAmount'));
                return;
            }

            setIsConverting(true);
            setConversionError(null);

            try {
                const conversionResult = await convertCurrency(fromCurrency, toCurrency, amountValue);
                setResult(conversionResult);
            } catch (err) {
                setConversionError(err instanceof Error ? err.message : t('unexpectedError'));
                setResult(null);
            } finally {
                setIsConverting(false);
            }
        },
        [amount, fromCurrency, toCurrency, t],
    );

    const handleSwapCurrencies = useCallback(() => {
        setFromCurrency(toCurrency);
        setToCurrency(fromCurrency);
    }, [fromCurrency, toCurrency]);

    if (loading) {
        return <div className="text-center py-8">{t('loadingCurrencies')}</div>;
    }

    if (error) {
        return (
            <div className="text-center py-8 text-red-600">
                <ErrorMessage message={error}/>
                <Button disabled={false} onClick={() => window.location.reload()}>
                    {t('retry')}
                </Button>
            </div>
        );
    }

    return (
        <div className="max-w-md mx-auto p-6 bg-white rounded-lg shadow-lg">
            <h2 className="text-2xl font-bold text-center mb-6">{t('currencyConverter')}</h2>

            <form onSubmit={handleSubmit}>
                <AmountInput
                    value={amount}
                    setAmount={setAmount}
                    currencyCode={fromCurrency}
                    disabled={isConverting}
                />

                <CurrencySelect
                    currencies={currencies}
                    value={fromCurrency}
                    onChange={setFromCurrency}
                    label={t('fromCurrency')}
                    id="from-currency"
                    disabled={isConverting}
                />

                <SwapButton onClick={handleSwapCurrencies} disabled={isConverting}/>

                <CurrencySelect
                    currencies={currencies}
                    value={toCurrency}
                    onChange={setToCurrency}
                    label={t('toCurrency')}
                    id="to-currency"
                    disabled={isConverting}
                />

                {conversionError && <ErrorMessage message={conversionError}/>}

                <Button type="submit" disabled={isConverting}>
                    {isConverting ? t('converting') : t('convert')}
                </Button>
            </form>

            {result && <ConversionResultDisplay result={result}/>}
        </div>
    );
};

export default CurrencyConverter;