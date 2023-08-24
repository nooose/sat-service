import React, {useEffect} from 'react';
import {useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";

export const Login = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
  const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
  const grantType = "authorization_code";

  useEffect(() => {
    const codeVerifier = sessionStorage.getItem("code_verifier");
    exchangeCodeForAccessToken(grantType, searchParams.get('code'), codeVerifier);
  }, []);

  const exchangeCodeForAccessToken = (grantType, code, codeVerifier) => {
    axios.post(
        `https://kauth.kakao.com/oauth/token`, { },
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
      const { access_token } = response.data;
      getJwt(access_token);
    })
    .catch(error => {
      console.log(error)
    });
  }

  const getJwt = (accessToken) => {
    axios.post(
        "/login/kakao",
        { "accessToken": accessToken },
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
    navigate("/");
  }
};

export default Login;
