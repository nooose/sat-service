import {Link, NavbarItem} from "@nextui-org/react";
import {cookies} from "next/headers";
import SatUser from "@/model/domain/SatUser";
import React from "react";
import UserProfile from "@/components/user-profile";
import {RestClient} from "@/utils/rest-client";

export async function getUserInfo(cookie: string | undefined): Promise<SatUser> {
    const response = await RestClient.get("/user/members/me")
        .session(cookie)
        .fetch();

    if (!response.ok) {
        return SatUser.unauthenticated();
    }
    const json = await response.json();
    return new SatUser(
        json.id,
        json.name,
        json.nickname,
        json.email,
        json.avatar,
        json.point,
    );
}

export default async function UserLogin() {
    const cookie = cookies().get("JSESSIONID")?.value
    const userInfo = await getUserInfo(cookie);
    const isAuthenticated = userInfo.isAuthenticated();
    return (
        <div>
            {isAuthenticated && (
                <div  className="hidden lg:flex">
                    <NavbarItem>
                        <UserProfile
                            id={userInfo.id}
                            name={userInfo.nickname}
                            point={userInfo.point}
                            avatar={userInfo.avatar}
                            cookie={cookie!!}
                        />
                    </NavbarItem>
                </div>
            )}
            {!isAuthenticated && (
                <NavbarItem className="hidden lg:flex">
                    <Link href={process.env.LOGIN_REQUEST_URL}>로그인</Link>
                </NavbarItem>
            )}
        </div>
    );
}
