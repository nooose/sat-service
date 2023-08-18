import React from 'react';
import loginImage from "./images/kakao_login_medium_narrow.png";
import {Button} from "@mui/material";

const KakaoLogin = () => {
    const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
    const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`

    const handleLogin = () => {
        window.location.href = kakaoURL;
    };

    return (
        <Button onClick={handleLogin}>
            <img src={loginImage} alt="Login"></img>
        </Button>
    );
}

export default KakaoLogin;
