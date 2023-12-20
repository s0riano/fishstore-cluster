/*
import React, { useEffect, useState } from 'react';
import ApiService from "../../shared/services/api-client/apiService";
import StoreBox from "./StoreBox";
import './StoreDisplay.css';
import LoadingFishingRodEmoji from "../../shared/loading/fishingrodEmoji/LoadingFishingRodEmoji";

const StoreDisplay: React.FC = () => {
    const [stores, setStores] = useState([]);

    useEffect(() => {
        async function fetchStores() {
            try {
                const fetchedStores = await ApiService.fetchData('/api/shops/active/all');
                console.log(fetchedStores)
                if (Array.isArray(fetchedStores)) {
                    setStores(fetchedStores);
                } else {
                    console.error("API did not return an array:", fetchedStores);
                }
            } catch (error) {
                console.error('Failed to fetch stores:', error);
            }
        }

        fetchStores();
        //return () => {};  // No-op cleanup function
    }, []);  // The empty dependency array means this useEffect will run once when the component mounts

    return (
        <div className="storeDisplayContainer">
            {/!*<h1>Stores</h1>*!/}
            <ul className="storeList">
                {
                    !stores.length ?
                        <LoadingFishingRodEmoji /> :
                        stores.map(store => <StoreBox key={store.id} store={store} />)
                }
            </ul>
        </div>
    );
};

export default StoreDisplay;
*/
