import React, {useEffect, useState} from 'react';
import ApiService from "../../../shared/services/api-client/apiService";
import LoadingFishingRodEmoji from "../../../shared/loading/fishingrodEmoji/LoadingFishingRodEmoji";
import StoresList from "./StoresList";

const StoreSection: React.FC = () => {
    const [stores , setStores] = useState([]);

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
            <ul className="storeList">
                {
                    !stores.length ?
                        <LoadingFishingRodEmoji /> :
                        stores.map(store => <StoresList /*key={store.id} store={store}*/ />)
                }
            </ul>
        </div>
    );
};

export default StoreSection;
