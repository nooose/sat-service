"use client"

import React, {useEffect} from "react";
import authStore from "@/store/auth-store";
import {get} from "@/utils/client";

export function LoginProvider({children}: { children: React.ReactNode }) {
    const store = authStore((state: any) => state);
    const fetchData = async () => {
        if (store.authenticated) {
            return;
        }

        const response = await get("/user/members/me");
        if (response.ok) {
            const json = await response.json();
            store.setName(json.name);
            store.setAuthenticated(true);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <React.Fragment>
            {children}
        </React.Fragment>
    )
}