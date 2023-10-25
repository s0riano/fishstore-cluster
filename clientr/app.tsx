import React from 'react';
import StoreDisplay from "./src/app/features/store/StoreDisplay";
import AddCatch from "./src/app/features/fisherman/catch/AddCatch";


export const App: React.FC = () => {


    return (
        <div>
            <StoreDisplay />
            <AddCatch />
        </div>
    )
}

//export default App;