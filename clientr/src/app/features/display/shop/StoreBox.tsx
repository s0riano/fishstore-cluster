import React from 'react';
import { DisplayStore } from './StoreTypes';
import './StoreBox.css';
import testImage from '../../../../assets/img/kaisalg.jpeg';
import {
    Card,
    CardHeader,
    CardBody,
    CardFooter,
    Typography,
    Button,
} from "@material-tailwind/react";



interface StoreItemProps { //go deeper on why this needs to be made
    store: DisplayStore;
}

/*fetch('/api/stores')
    .then(response => response.json())
    .then((data: Store[]) => {
        // Now `data` is typed as an array of Stores
        console.log(data);
    });*/

const StoreBox: React.FC<StoreItemProps> = ({ store }) => {

    return (
        <div className="flex justify-center items-center bg-white rounded-full p-4 shadow-md h-32 w-full">
            <p className="text-lg font-semibold">{store.name}</p>

            <Card className="mt-6 w-96">
                <CardHeader color="blue-gray" className="relative h-56">
                    <img
                        src={testImage} // Change to S3 in the future
                        alt="Store"
                    />
                </CardHeader>
                <CardBody>
                    <Typography variant="h5" color="blue-gray" className="mb-2">
                        UI/UX Review Check
                    </Typography>
                    <Typography>
                        {store.description}
                    </Typography>
                </CardBody>
                <CardFooter className="pt-0">
                    <Typography>{store.nextDate}</Typography>
            </CardFooter>
            </Card>
        </div>

    );
};

export default StoreBox;