import React from 'react';
import {Button} from "@mui/material";
import loginImage from "./images/kakao_login_medium_narrow.png";
import {navigateRelativePath} from "../common/common";

const LoginButton = () => {
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
