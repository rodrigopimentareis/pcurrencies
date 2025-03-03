import {renderHook, waitFor} from '@testing-library/react';
import {useCurrencies} from '../useCurrencies';
import {fetchSupportedCurrencies} from '../../services/api';
import {Currency} from "../../types";

jest.mock('../../services/api', () => ({
    fetchSupportedCurrencies: jest.fn() as jest.Mock<Promise<Currency[]>>,
}));

function checkInitialState(result: any) {
    expect(result.current.loading).toBe(true);
    expect(result.current.error).toBeNull();
    expect(result.current.currencies).toEqual([]);
}

describe('useCurrencies with renderHook', () => {
    beforeEach(() => {
        jest.clearAllMocks();
    });

    test('should fetch and return currencies', async () => {
        // Given
        (fetchSupportedCurrencies as jest.Mock).mockResolvedValueOnce([
            {code: 'USD'},
            {code: 'EUR'},
            {code: 'BRL'},
        ]);
        const {result} = renderHook(() => useCurrencies());
        checkInitialState(result);

        // When
        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        // Then
        expect(result.current.error).toBeNull();
        expect(result.current.currencies).toEqual([
            {code: 'USD'},
            {code: 'EUR'},
            {code: 'BRL'},
        ]);
    });

    test('should handle errors', async () => {
        (fetchSupportedCurrencies as jest.Mock).mockRejectedValueOnce(new Error('Failed to fetch'));
        const {result} = renderHook(() => useCurrencies());
        checkInitialState(result);

        // Wait for the hook to finish loading
        await waitFor(() => {
            expect(result.current.loading).toBe(false);
        });

        // Check the final state
        expect(result.current.error).toBe('Failed to load currencies. Please try again later.');
        expect(result.current.currencies).toEqual([]);
    });
});