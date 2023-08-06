import React, {useEffect} from 'react';
import {useNavigate, useSearchParams} from "react-router-dom";
import axios from "axios";

export const Login = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const navigate = useNavigate();

    useEffect(() => {
        requestLogin(searchParams.get('code'));
    }, []);

    const requestLogin = (code) => {
        axios.get('/login', {
                params: {
                    code: code,
                }
            }
        )
            .then(response => {
                localStorage.setItem("sat-access-token", response.data.accessToken);
                localStorage.setItem("sat-refresh-token", response.data.refreshToken);
                navigate("/");
            })
            .catch(error => {
                console.log(error)
            });
    }
};

export default Login;
