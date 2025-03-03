import React, {useCallback} from 'react';
import {useTranslation} from "react-i18next";

interface AmountInputProps {
    value: string;
    setAmount: (value: string) => void;
    currencyCode: string;
    disabled?: boolean;
}

const AmountInput: React.FC<AmountInputProps> = ({value, currencyCode, disabled, setAmount}) => {
    const {t} = useTranslation();

    const handleAmountChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value.replace(/[^0-9.]/g, '');
        if (value.split('.').length > 2) return;
        setAmount(value);
    }, []);

    return (
        <div className="mb-4">
            <label htmlFor="amount" className="block text-sm font-medium text-gray-700 mb-1">
                {t('amount')}
            </label>
            <div className="mt-1 relative rounded-md shadow-sm">
                <input
                    type="text"
                    id="amount"
                    value={value}
                    onChange={handleAmountChange}
                    className="focus:ring-indigo-500 focus:border-indigo-500 block w-full pl-3 pr-12 sm:text-sm border-gray-300 rounded-md"
                    placeholder="0.00"
                    aria-describedby="amount-currency"
                    disabled={disabled}
                />
                <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
                    <span className="text-gray-500 sm:text-sm" id="amount-currency">
                        {currencyCode || '???'}
                    </span>
                </div>
            </div>
        </div>
    );
};

export default AmountInput;