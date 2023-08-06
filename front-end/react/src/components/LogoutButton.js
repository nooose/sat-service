import React from 'react';
import {Button} from "@mui/material";
import {useDispatch} from "react-redux";
import {logout} from "../store/action";
import {navigateRelativePath} from "../common/common";

const LogoutButton = () => {
    const dispatch = useDispatch();

    const handleLogout = () => {
        localStorage.removeItem("sat-access-token");
        dispatch(logout());
        navigateRelativePath("/");
    };

    return (
        <Button variant="contained" onClick={handleLogout}>
            로그아웃
        </Button>
    );
};

export default LogoutButton;
