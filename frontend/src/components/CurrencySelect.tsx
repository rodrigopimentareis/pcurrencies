import React from 'react';
import {Currency} from '../types';
import {useTranslation} from "react-i18next";

interface CurrencySelectProps {
    currencies: Currency[];
    value: string;
    onChange: (value: string) => void;
    label: string;
    id: string;
    disabled?: boolean;
}

const CurrencySelect: React.FC<CurrencySelectProps> = ({
                                                           currencies,
                                                           value,
                                                           onChange,
                                                           label,
                                                           id,
                                                           disabled = false
                                                       }) => {
    const {t} = useTranslation();
    return (
        <div className="mb-4">
            <label htmlFor={id} className="block text-sm font-medium text-gray-700 mb-1">
                {label}
            </label>
            <select
                id={id}
                value={value}
                onChange={(e) => onChange(e.target.value)}
                disabled={disabled}
                className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none sm:text-sm rounded-md"
            >
                <option value="" disabled>
                    {t('selectCurrency')}
                </option>
                {currencies.map((currency) => (
                    <option key={currency.code} value={currency.code}>
                        {currency.code}
                    </option>
                ))}
            </select>
        </div>
    );
};

export default CurrencySelect;