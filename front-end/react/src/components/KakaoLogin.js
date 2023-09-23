import React from 'react';
import loginImage from "./images/kakao_login_medium_narrow.png";
import {Button} from "@mui/material";
import pkce from 'oauth-pkce';

const KakaoLogin = () => {
    const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;

    const handleLogin = async () => {
        pkce(32, (error, { verifier, challenge }) => {
            if (!error) {
                sessionStorage.setItem("code_verifier", verifier);
                sessionStorage.setItem("code_challenge", challenge);
                window.location.href = kakaoOAuthURL(challenge);
            }
        });
    };

    const kakaoOAuthURL = (codeChallenge) => {
        return `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code&code_challenge=${codeChallenge}&code_challenge_method=S256`
    }

    return (
        <Button onClick={handleLogin}>
            <img src={loginImage} alt="Login"></img>
        </Button>
    );
}

export default KakaoLogin;
