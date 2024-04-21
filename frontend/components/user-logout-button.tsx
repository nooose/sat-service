"use client"

import {Button} from "@nextui-org/react";
import React from "react";
import {get} from "@/utils/client";
import {useRouter} from "next/navigation";

export default function LogoutButton({cookie}: { cookie: string }) {
    const router = useRouter();
    function logout() {
        get("/logout", cookie);
        return router.push("/");
    }

    return (
        <Button onClick={logout}>로그아웃</Button>
    )
};