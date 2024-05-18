"use client"

import React, {useState} from "react";
import {Button} from "@nextui-org/react";
import {Input} from "@nextui-org/input";
import MemberUpdateRequest from "@/model/dto/request/MemberUpdateRequest";
import {RestClient} from "@/utils/rest-client";
import {errorToast, successToast} from "@/utils/toast-utils";
import {useRouter} from "next/navigation";

export default function ClientMypageInfoUpdate({nickname}: { nickname: string }) {
    const [toggleEditButton, setToggleEditButton] = useState(false);
    const [requestNickname, setRequestNickname] = useState(nickname)
    const router = useRouter();

    const updateMember = () => {
        const request: MemberUpdateRequest = {
            nickname: requestNickname,
        };
        RestClient.put(`/user/members/me`)
            .requestBody(request)
            .successHandler(() => {
                successToast("닉네임 변경이 완료되었습니다.");
                router.refresh();
            })
            .errorHandler((error) => {
                errorToast(error.errorMessage());
            })
            .fetch();
    }

    return (
        <div className="flex gap-5">
            <Input type="text"
                   label="닉네임"
                   disabled={!toggleEditButton}
                   value={requestNickname}
                   onChange={event => setRequestNickname(event.target.value)}
            />
            <Button
                color={toggleEditButton ? "warning" : "default"}
                onClick={(event) => {
                    setToggleEditButton(!toggleEditButton);
                        if (toggleEditButton) {
                            updateMember();
                        }
                    }
                }
            >
                닉네임 변경
            </Button>
        </div>
    );
}
