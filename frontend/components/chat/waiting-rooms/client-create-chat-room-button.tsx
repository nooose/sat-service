"use client"

import React from "react";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export default function ClientCreateChatRoomButton() {
    const router = useRouter();

    const createChatRoom = () => {
        router.push('/chat/create');
    }

    return (
        <div>
            <Button
                color="primary"
                onClick={createChatRoom}>
                채팅방 생성
            </Button>
        </div>
    )
}