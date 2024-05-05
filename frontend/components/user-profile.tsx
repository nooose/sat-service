"use client"

import {DropdownItem, DropdownMenu, User} from "@nextui-org/react";
import React from "react";
import {Dropdown, DropdownTrigger} from "@nextui-org/dropdown";
import {useRouter} from "next/navigation";
import {RestClient} from "@/utils/restClient";

export default function UserProfile({name, nickname, avatar, cookie}: {
    name: string,
    nickname: string,
    avatar: string,
    cookie: string
}) {
    const router = useRouter();

    function logout() {
        RestClient.get("/logout")
            .session(cookie)
            .successHandler(() => {
                router.push("/");
                router.refresh();
            })
            .fetch();
    }

    return (
        <Dropdown placement="bottom-start">
            <DropdownTrigger>
                <User
                    as="button"
                    avatarProps={{
                        isBordered: true,
                        src: avatar,
                    }}
                    className="transition-transform"
                    description={nickname}
                    name={name}
                />
            </DropdownTrigger>
            <DropdownMenu aria-label="User Actions" variant="flat">
                <DropdownItem key="logout" color="danger" onClick={logout}>
                    로그아웃
                </DropdownItem>
            </DropdownMenu>
        </Dropdown>
    );
}