import React from 'react';
import { AppConstants } from '../util/constants.js';    

export const AppContext = React.createContext();

export const AppContextProvider = (props) => {

    const backendUrl = AppConstants.BACKEND_URL;
    const [isLoggedIn, setIsLoggedIn] = React.useState(false);
    const [userData, setUserData] = React.useState(false);

    const contextValue = {
        // Define any context values or functions here
        backendUrl,
        isLoggedIn,
        setIsLoggedIn,
        userData,
        setUserData 
    };

    return (
        <AppContext.Provider value={contextValue}>
            {props.children}
        </AppContext.Provider>
    );
}
