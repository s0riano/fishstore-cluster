import React from 'react';
import './src/index.css';
import StoreDisplay from "./app/features/store/StoreDisplay";
import AddCatch from "./app/features/fisherman/catch/AddCatch";

import LandingPage from "./app/features/display/LandingPage";

export const App: React.FC = () => {


    return (
        <div>
                <LandingPage />
                {/*<StoreDisplay />
                <AddCatch />*/}
        </div>
    )
}

//export default App;