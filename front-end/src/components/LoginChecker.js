import React, {useEffect} from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import Cookies from "js-cookie";
import {login, logout} from "../store/action";

export const LoginChecker = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        const token = getAccessToken();
        if (token !== null) {
            memberQuery();
        }
    }, []);

    const getAccessToken = () => {
        syncLocalStorageWithCookie("sat-access-token")
        return localStorage.getItem("sat-access-token");
    }

    const syncLocalStorageWithCookie = (key) => {
        if (localStorage.getItem(key) === null) {
            localStorage.setItem(key, Cookies.get(key));
            Cookies.remove(key);
        }
    }

    const memberQuery = (token) => {
        axios.get(window.location.origin + '/v1/members/me', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        })
            .then(response => {
                console.log(response.data)
                dispatch(login(response.data));
            })
            .catch(error => {
                console.log(error)
                dispatch(logout());
            });
    }
};

export default LoginChecker;
