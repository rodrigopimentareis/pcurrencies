import React from 'react';
import {ConversionResult} from '../types';

interface ConversionResultProps {
    result: ConversionResult | null;
}

const ConversionResultDisplay: React.FC<ConversionResultProps> = ({result}) => {
    if (!result) return null;

    const formatCurrency = (amount: number, currencyCode: string) => {
        return new Intl.NumberFormat(navigator.language, {
            style: 'currency',
            currency: currencyCode,
        }).format(amount);
    };

    const formattedConvertedAmount = formatCurrency(result.quote, result.quoteCurrency);

    return (
        <div className="mt-6 bg-green-50 border border-green-200 rounded-md p-4">
            <h3 className="text-lg font-medium text-green-800">Conversion Result</h3>
            <p id='conversion-result' className="text-2xl font-bold mb-1">{formattedConvertedAmount}</p>
        </div>
    );
};

export default ConversionResultDisplay;