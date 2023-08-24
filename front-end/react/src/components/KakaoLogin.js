import React from 'react';
import loginImage from "./images/kakao_login_medium_narrow.png";
import {Button} from "@mui/material";
import Cookies from 'js-cookie';

const KakaoLogin = () => {
    const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;

    const handleLogin = async () => {
        await generateCodeChallenge();
        const codeChallenge = Cookies.get('code_challenge');
        window.location.href = kakaoOAuthURL(CLIENT_ID, REDIRECT_URI, codeChallenge);
    };

    const generateCodeChallenge = () => {
        const codeVerifier = generateCodeVerifier(64);
        generateS256Code(codeVerifier)
        .then(codeChallenge => {
            console.log(codeChallenge);
            Cookies.set('code_verifier', codeVerifier);
        })
        .catch(error => {
            console.error(error);
        });
    }

    const generateCodeVerifier = (length) => {
        const charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
        const randomBytes = new Uint8Array(length);
        crypto.getRandomValues(randomBytes);
        return [...randomBytes].map(byte => charset[byte % charset.length]).join('');
    }

    const generateS256Code = (codeVerifier) => {
        return crypto.subtle.digest("SHA-256", new TextEncoder().encode(codeVerifier))
        .then(buffer => {
            const hashedCodeVerifier = new Uint8Array(buffer);
            return base64Encoding(hashedCodeVerifier);
        });
    }

    function base64Encoding(buffer) {
        return btoa(String.fromCharCode.apply(null, buffer))
        .replace(/\+/g, '-').replace(/\//g, '_').replace(/=+$/, '');
    }

    const kakaoOAuthURL = (clientId, redirectUri, codeChallenge) => {
        return `https://kauth.kakao.com/oauth/authorize?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code&code_challenge=${codeChallenge}&code_challenge_method=S256`
    }


    return (
        <Button onClick={handleLogin}>
            <img src={loginImage} alt="Login"></img>
        </Button>
    );
}

export default KakaoLogin;
