import React, {createContext, useContext, useEffect, useLayoutEffect, useState} from 'react';

const AuthContext = createContext();

export const useAuth = () => {
    return useContext(AuthContext);
};

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useLayoutEffect(() => {
        const token = localStorage.getItem("sat-access-token");
        if (token == null) {
            setIsLoggedIn(false);
            return;
        }
        setIsLoggedIn(true);
    }, []);

    const login = () => {
        setIsLoggedIn(true);
    };

    const logout = () => {
        localStorage.removeItem("sat-access-token");
        localStorage.removeItem("sat-refresh-token");
        setIsLoggedIn(false);
    };

    return (
        <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};
