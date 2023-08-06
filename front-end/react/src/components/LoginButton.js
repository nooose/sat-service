import React from 'react';
import {Button} from "@mui/material";
import loginImage from "./images/kakao_login_medium_narrow.png";
import {navigateRelativePath} from "../common/common";
import {useNavigate} from "react-router-dom";

const LoginButton = () => {
    const navigate = useNavigate();

    const handleLogin = () => {
        navigateRelativePath("/oauth2/authorization/kakao");
    };

    return (
        <Button onClick={handleLogin}>
            <img src={loginImage} alt="Login"></img>
        </Button>
    );
};

export default LoginButton;
