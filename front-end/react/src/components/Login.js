import React, {useEffect} from 'react';
import {useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";

export const Login = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  useEffect(() => {
    const grantType = "authorization_code";
    const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
    const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
    exchangeCodeForAccessToken(grantType, CLIENT_ID, REDIRECT_URI,
        searchParams.get('code'));
  }, []);

  const exchangeCodeForAccessToken = (grantType, clientId, redirectUri, code) => {
    axios.post(
        `https://kauth.kakao.com/oauth/token?grant_type=${grantType}&client_id=${clientId}&redirect_uri=${redirectUri}&code=${code}`,
        {},
        {
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
