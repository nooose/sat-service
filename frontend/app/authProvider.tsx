import React, {useEffect, useLayoutEffect, useState} from "react";
import authStore from "@/store/auth-store";
import {get} from "@/utils/client";

export function LoginProvider({children}: { children: React.ReactNode }) {
    const store = authStore((state: any) => state);
    const [ok, setOk] = useState(false);

    const fetchData = async () => {
        if (store.authenticated) {
            return;
        }
        const response = await get("/user/members/me")
            .finally(() => setOk(true));
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
            {ok && children}
        </React.Fragment>
    )
}