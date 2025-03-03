export interface Currency {
    code: string;
}

export interface ConversionResult {
    baseCurrency: string;
    quoteCurrency: string;
    quote: number;
}
