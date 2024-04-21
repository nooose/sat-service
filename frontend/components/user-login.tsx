import {Link, NavbarItem} from "@nextui-org/react";
import {get} from "@/utils/client";
import {cookies} from "next/headers";
import User from "@/model/domain/User";
import React from "react";
import LogoutButton from "@/components/user-logout-button";

export async function getUserInfo(cookie: string | undefined): Promise<User> {
    const response = await get("/user/members/me", cookie);
    if (response.ok) {
        const json = await response.json();
        return new User(
            json.id,
            json.name,
            json.nickname,
            json.email
        );
    }
    return User.unauthenticated();
}

export default async function UserLogin() {
    const cookie = cookies().get("JSESSIONID")?.value
    const userInfo = await getUserInfo(cookie);
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