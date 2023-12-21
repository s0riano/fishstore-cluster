import React, { useState, useEffect } from 'react';
import StoreBox from './StoreBox';
import { DisplayStore } from './StoreTypes';

const StoresList: React.FC = () => {
    const [stores, setStores] = useState<DisplayStore[]>([]);

    useEffect(() => {
        fetch('/api/stores')
            .then(response => response.json())
            .then((data: DisplayStore[]) => {
                setStores(data);
            })
            .catch(error => {
                // Handle the error
                console.error('Error fetching stores:', error);
            });
    }, []);

    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
            {stores.map(store => (
                <StoreBox key={store.id} store={store} />
            ))}
        </div>
    );
};

export default StoresList;
