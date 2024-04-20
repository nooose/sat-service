import React, {useEffect, useLayoutEffect, useState} from "react";
import authStore from "@/store/auth-store";
import {get} from "@/utils/client";
import Loading from "@/app/loading";

export function LoginProvider({children}: { children: React.ReactNode }) {
    const store = authStore((state: any) => state);
    const [ok, setOk] = useState(false);

    const fetchData = async () => {
        if (store.authenticated) {
            return;
        }
        await get("/user/members/me")
            .then(async response => {
                const json = await response.json();
                store.setName(json.name);
                store.setAuthenticated(true);
            })
            .catch(error => {
            })
            .finally(() => setOk(true))
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <React.Fragment>
            {!ok && <Loading/>}
            {ok && children}
        </React.Fragment>
    )
}