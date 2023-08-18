import React, {useEffect, useLayoutEffect, useState} from 'react';
import {
    AppBar, Toolbar, Typography
} from "@mui/material";
import LogoutButton from "./LogoutButton";
import KakaoLogin from "./KakaoLogin";

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
                    <Typography variant="h4" sx={{flexGrow: 2}}>
                        SAT
                    </Typography>
                    {isLoggedIn ? (
                        <LogoutButton/>
                    ) : (
                        <KakaoLogin/>
                    )}
                </Toolbar>
            </AppBar>
        </div>
    );
};

export default Header;
