import CurrencyConverter from '../CurrencyConverter.tsx'
import {ConversionResult, Currency} from "../../types";

const mockCurrencies: Currency[] = [
    {code: 'USD'},
    {code: 'EUR'},
    {code: 'BRL'},
];

const mockConversionResult: ConversionResult = {
    baseCurrency: 'USD',
    quoteCurrency: 'EUR',
    quote: 500
};

function typeAmount(text: string) {
    cy.get('input[id="amount"]').type(text);
}

function selectFromCurrency(newValue: string) {
    cy.get('select[id="from-currency"]').select(newValue);
}

function selectToCurrency(newValue: string) {
    cy.get('select[id="to-currency"]').select(newValue);
    cy.get('select[id="to-currency"]').contains(newValue)
}

function clickConvert() {
    cy.get('button').contains('convert').click();
}

describe('CurrencyConverter', () => {
    beforeEach(() => {
        // Mock the API responses
        cy.intercept('GET', 'http://localhost:8080/currencies', {
            statusCode: 200,
            body: mockCurrencies,
        }).as('fetchCurrencies');

        cy.intercept('GET', 'http://localhost:8080/conversions/USD/EUR?amount=100', {
            statusCode: 200,
            body: mockConversionResult,
        }).as('convertCurrency');

        cy.intercept('GET', 'http://localhost:8080/conversions/EUR/BRL?amount=100', {
            statusCode: 400,
            body: mockConversionResult,
        }).as('convertCurrency');

        // Mount the component
        cy.mount(<CurrencyConverter/>);

        cy.wait('@fetchCurrencies');
    });

    it('should render the form', () => {
        cy.get('h2').should('contain', 'currencyConverter');
        cy.get('label').should('contain', 'amount');
        cy.get('label').should('contain', 'fromCurrency');
        cy.get('label').should('contain', 'toCurrency');
        cy.get('button').should('contain', 'convert');
    });

    it('should convert currency successfully', () => {
        // Given
        typeAmount('100');
        selectFromCurrency('USD');
        selectToCurrency('EUR');

        // When
        clickConvert();
        cy.wait('@convertCurrency');

        // Then
        cy.contains('€500.00');
    });

    it('should display an error for invalid amount', () => {
        // Given
        typeAmount('-100');
        // When
        clickConvert();
        // Then
        cy.contains('Currency not supported');
    });
});