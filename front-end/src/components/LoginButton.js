import React from 'react';
import {
    Button
} from "@mui/material";
import loginImage from "./images/kakao_login_medium_narrow.png";
import {navigateRelativePath} from "../common/common";

const LoginButton = () => {
    const handleLogin = () => {
        // TODO: 현재 Location 기록
        navigateRelativePath("/oauth2/authorization/kakao");
    };

    return (
        <div>
            <Button onClick={handleLogin}>
                <img src={loginImage} alt="Login"></img>
            </Button>
        </div>
    );
};

export default LoginButton;
