"use client"

import React from "react";
import {Input} from "@nextui-org/input";

export default function ClientMemberInfoNickname({nickname}: { nickname: string }) {
    console.log(nickname);
    return (
        <div className="flex gap-5">
            <Input type="text"
                   label="닉네임"
                   value={nickname}
                   readOnly={true}
            />
        </div>
    );
}
