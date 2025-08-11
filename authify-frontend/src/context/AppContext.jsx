import React from 'react';
import { AppConstants } from '../util/constants.js';    
import { toast } from 'react-toastify';
import axios from 'axios';

export const AppContext = React.createContext();

export const AppContextProvider = (props) => {

    const backendUrl = AppConstants.BACKEND_URL;
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [userData, setUserData] = React.useState(false);

    const getUserData = async () => {   
        try{
            const response = await axios.get(`${backendUrl}/profile`);
            if(response.status === 200) {
                setUserData(response.data);
                console.log('User data fetched successfully:', response.data);
            } else {
                toast.error('Failed to fetch user profile. Please try again.');
            }   
        } catch (error) {
            toast.error(error.response?.data?.message || 'An error occurred while fetching user data.');
        }   
    }

    const contextValue = {
        // Define any context values or functions here
        backendUrl,
        isLoggedIn,
        setIsLoggedIn,
        userData,
        setUserData,
        getUserData
    };

    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    );
}
