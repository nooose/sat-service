import React from 'react';
import {Button} from "@mui/material";
import {navigateRelativePath} from "../common/common";
import {useAuth} from "./AuthProvider";

const LogoutButton = () => {
    const { isLoggedIn, login, logout } = useAuth();

    const handleLogout = () => {
        logout();
        navigateRelativePath("/");
    };

    return (
        <Button variant="contained" onClick={handleLogout}>
            로그아웃
        </Button>
    );
};

export default LogoutButton;
