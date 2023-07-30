import React from 'react';
import {
    Button,
} from "@mui/material";
import {navigateRelativePath} from "../common/common";

const LogoutButton = () => {
    const handleLogout = () => {
        localStorage.removeItem("sat-access-token");
        navigateRelativePath("/")
    };

    return (
        <Button variant="contained" onClick={handleLogout}>
            로그아웃
        </Button>
    );
};

export default LogoutButton;
