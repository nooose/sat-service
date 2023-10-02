import React, {useEffect} from 'react';
import {useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";
import {useAuth} from "./AuthProvider";

export const OAuth2Login = () => {
    const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
    const API_SERVER = process.env.REACT_APP_API_SERVER;

    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();
    const grantType = "authorization_code";
    const {isLoggedIn, login, logout} = useAuth();

    useEffect(() => {
        const codeVerifier = sessionStorage.getItem("code_verifier");
        const authorizationCode = searchParams.get('code');
        exchangeCodeForAccessToken(grantType, authorizationCode, codeVerifier);
    }, []);

    const exchangeCodeForAccessToken = (grantType, code, codeVerifier) => {
        axios.post(
            `https://kauth.kakao.com/oauth/token`, {},
            {
                params: {
                    grant_type: grantType,
                    client_id: CLIENT_ID,
                    redirect_uri: REDIRECT_URI,
                    code: code,
                    code_verifier: codeVerifier
                },
                headers: {
                    "Content-type": "application/x-www-form-urlencoded;charset=utf-8"
                },
            }
        )
            .then(response => {
                console.log(response);
                const {access_token} = response.data;
                getJwt(access_token);
            })
            .catch(error => {
                console.log(error)
            });
    }

    const getJwt = (accessToken) => {
        axios.post(
            API_SERVER + "/kakao/token",
            {"accessToken": accessToken},
        )
            .then(response => {
                saveToken(response.data);
            })
            .catch(error => {
                console.log(error);
            });
    }

    const saveToken = (token) => {
        localStorage.setItem("sat-access-token", token.accessToken);
        localStorage.setItem("sat-refresh-token", token.refreshToken);
        login();
        navigate("/");
    }

    return (
        <div/>
    );
};

export default OAuth2Login;
