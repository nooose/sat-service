import React, {useEffect, useLayoutEffect, useState} from 'react';
import {
    AppBar, Toolbar, Typography
} from "@mui/material";
import LogoutButton from "./LogoutButton";
import LoginButton from "./LoginButton";

const Header = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useLayoutEffect(() => {
        const token = localStorage.getItem("sat-access-token");
        if (token == null) {
            setIsLoggedIn(false);
            return;
        }
        setIsLoggedIn(true);
    }, []);

    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h3">
                        SAT
                    </Typography>
                    {isLoggedIn ? (
                        <LogoutButton/>
                    ) : (
                        <LoginButton/>
                    )}
                </Toolbar>
            </AppBar>
        </div>
    );
};

export default Header;
