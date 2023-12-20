import React, { useState } from 'react';
import ApiService from "../../../shared/services/api-client/apiService";
import { ICatch } from './types';  // Import the interface

const AddCatch = () => {
    const [sellerId, setSellerId] = useState<number | ''>('');  // TypeScript requires us to be explicit about types
    const [seafoodType, setSeafoodType] = useState<string>('');
    const [kilos, setKilos] = useState<number | ''>('');
    const [errorMessage, setErrorMessage] = useState<string | null>(null);  // Add this state for the error message

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setErrorMessage(null);

        if (typeof sellerId === 'number' && typeof kilos === 'number') {
            const catchData: ICatch = {
                seafood_type: seafoodType,
                kilos
            };

            try {
                const response = await ApiService.postData(`api/catches/${sellerId}`, catchData);
                console.log(response);
                console.log("Data posted to the database:", catchData);
            } catch (error) {
                console.error(error);
                if (error.response && error.response.status === 503) {
                    setErrorMessage("The service is currently unavailable. Please try again later.");
                } else {
                    setErrorMessage("An unexpected error occurred.");
                }
            }
        } else {
            console.error("Invalid data");
            setErrorMessage("Please provide valid data.");
        }
    };


    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Seller ID:</label>
                    <input type="number" value={sellerId.toString()} onChange={e => setSellerId(Number(e.target.value))} />
                </div>
                <div>
                    <label>Seafood Type:</label>
                    <input type="text" value={seafoodType} onChange={e => setSeafoodType(e.target.value)} />
                </div>
                <div>
                    <label>Kilos:</label>
                    <input type="number" step="0.1" value={kilos.toString()} onChange={e => setKilos(Number(e.target.value))} />
                </div>
                <button type="submit">Add Catch</button>
            </form>
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
        </div>
    );
};

export default AddCatch;

