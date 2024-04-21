import {Button, Link, NavbarItem} from "@nextui-org/react";
import {get} from "@/utils/client";
import {cookies} from "next/headers";
import SatUser from "@/model/domain/User";
import React from "react";
import LogoutButton from "@/components/user-logout-button";

const cookie = cookies().get("JSESSIONID")?.value

async function getUserInfo() : Promise<SatUser> {
    const response = await get("/user/members/me", cookie);
    if (response.ok) {
        const json = await response.json();
        return new SatUser(
            json.id,
            json.name,
            json.nickname,
            json.email
        );
    }
    return SatUser.unauthenticated();
}

export default async function UserLogin() {
    const userInfo = await getUserInfo();
    const isAuthenticated = userInfo.isAuthenticated();
    console.log(userInfo);
    return (
        <div>
            {isAuthenticated && (
                <NavbarItem className="hidden lg:flex">
                    <span>{userInfo.name}</span>
                    <LogoutButton cookie={cookie!!}/>
                </NavbarItem>
            )}
            {!isAuthenticated && (
                <NavbarItem className="hidden lg:flex">
                    <Link href="http://localhost:8080/oauth2/authorization/kakao">로그인</Link>
                </NavbarItem>
            )}
        </div>
    );
}