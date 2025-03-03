import React from 'react';

interface ButtonProps {
    onClick?: () => void;
    disabled?: boolean;
    type?: 'button' | 'submit' | 'reset';
    className?: string;
    children: React.ReactNode;
}

const Button: React.FC<ButtonProps> = ({onClick, disabled, type = 'button', className, children}) => {
    return (
        <button
            type={type}
            onClick={onClick}
            disabled={disabled}
            className={`w-full flex justify-center py-3 px-4 border border-transparent rounded-md shadow-sm text-sm bg-gray-100 hover:bg-gray-200 
            font-medium text-black ${className}`}
        >
            {children}
        </button>
    );
};

export default Button;