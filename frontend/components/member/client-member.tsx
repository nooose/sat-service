"use client"

import {DropdownItem, DropdownMenu, Image} from "@nextui-org/react";
import React from "react";
import {Dropdown, DropdownTrigger} from "@nextui-org/dropdown";
import {useRouter} from "next/navigation";

export default function ClientMember({ memberId, memberName }: { memberId: number, memberName: string }) {
    const router = useRouter();

    const memberPage = () => {
        router.push(`/member/${memberId}/info`);
    };
    return (
        <div>
            <Dropdown placement="bottom-start">
                <DropdownTrigger>
                    <div className={"flex gap-2 items-center"}>
                        <Image
                            alt="avatar"
                            height={30}
                            radius="sm"
                            src="https://avatars.githubusercontent.com/u/86160567?s=200&v=4"
                            width={30}
                        />
                        <p>{memberName}</p>
                    </div>
                </DropdownTrigger>
                <DropdownMenu aria-label="User Actions" variant="flat">
                    <DropdownItem key="mypage" onClick={memberPage}>
                        유저 정보 조회
                    </DropdownItem>
                </DropdownMenu>
            </Dropdown>
        </div>
    )
}
