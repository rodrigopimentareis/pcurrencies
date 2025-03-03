import React from 'react';
import Button from "./Button.tsx";

interface SwapButtonProps {
    onClick: () => void;
    disabled?: boolean;
}

const SwapButton: React.FC<SwapButtonProps> = ({onClick, disabled}) => {
    return (
        <div className="flex justify-center my-4">
            <Button
                type="button"
                onClick={onClick}
                disabled={disabled}>
                <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 text-gray-600" fill="none"
                     viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2}
                          d="M7 16V4m0 0L3 8m4-4l4 4m6 0v12m0 0l4-4m-4 4l-4-4"/>
                </svg>
            </Button>
        </div>
    );
};

export default SwapButton;