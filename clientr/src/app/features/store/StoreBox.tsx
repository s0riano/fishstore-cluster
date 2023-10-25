import React from 'react';
import { Store } from './StoreTypes';
import './StoreBox.css';

interface StoreItemProps {
    store: Store;
}

const StoreBox: React.FC<StoreItemProps> = ({ store }) => {
    return (
        <li key={store.id}>
            {store.name}
            {/* Render other fields of the store as needed */}
        </li>
    );
};

export default StoreBox;
