import './App.css'
import CurrencyConverter from "./components/CurrencyConverter.tsx";

const App: React.FC = () => {
    return (
        <div className="min-h-screen bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-md mx-auto">
                <CurrencyConverter/>
            </div>
        </div>
    );
};

export default App
