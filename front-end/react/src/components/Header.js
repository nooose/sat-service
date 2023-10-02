import React, {useEffect, useLayoutEffect, useState} from 'react';
import {
    AppBar, Toolbar, Typography
} from "@mui/material";
import LogoutButton from "./LogoutButton";
import KakaoLogin from "./KakaoLogin";
import {useAuth} from "./AuthProvider";

const Header = () => {
    const { isLoggedIn } = useAuth();

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
