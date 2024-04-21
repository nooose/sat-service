import {Link, NavbarItem} from "@nextui-org/react";
import {get} from "@/utils/client";
import {cookies} from "next/headers";
import SatUser from "@/model/domain/SatUser";
import React from "react";
import UserProfile from "@/components/user-profile";

export async function getUserInfo(cookie: string | undefined): Promise<SatUser> {
    const response = await get("/user/members/me", cookie);
    if (response.ok) {
        const json = await response.json();
        return new SatUser(
            json.id,
            json.name,
            json.nickname,
            json.email,
            json.avatar,
        );
    }
    return SatUser.unauthenticated();
}

export default async function UserLogin() {
    const cookie = cookies().get("JSESSIONID")?.value
    const userInfo = await getUserInfo(cookie);
    const isAuthenticated = userInfo.isAuthenticated();
    return (
        <div>
            {isAuthenticated && (
                <NavbarItem className="hidden lg:flex">
                    <UserProfile
                        name={userInfo.name}
                        nickname={userInfo.nickname}
                        avatar={userInfo.avatar}
                        cookie={cookie!!}
                    />
                </NavbarItem>
            )}
            {!isAuthenticated && (
                <NavbarItem className="hidden lg:flex">
                    <Link href={process.env.LOGIN_REQUEST_URL}>로그인</Link>
                </NavbarItem>
            )}
        </div>
    );
}