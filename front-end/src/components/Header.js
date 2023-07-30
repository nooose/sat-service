import React from 'react';
import {useSelector} from 'react-redux';
import {
    AppBar, Toolbar, Typography
} from "@mui/material";
import LogoutButton from "./LogoutButton";
import LoginButton from "./LoginButton";

const Header = () => {
    const authState = useSelector((state) => state.auth);
    console.log("인증: " + authState.isLoggedIn)

    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h4" sx={{flexGrow: 2}}>
                        SAT
                    </Typography>
                    {authState.isLoggedIn ? (
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
