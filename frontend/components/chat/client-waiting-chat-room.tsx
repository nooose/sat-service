"use client"

import ChatRoomResponse from "@/model/dto/response/ChatRoomResponse";
import {Card, CardBody, CardFooter, CardHeader} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export default function ClientWaitingChatRoom({chatRoom} : {chatRoom: ChatRoomResponse}) {
    const router = useRouter();
    const goChatRoom = () => {
        router.push(`/chat/${chatRoom.id}`);
        router.refresh();
    }

    return (
        <Card>
            <CardBody onClick={goChatRoom}>
                {chatRoom.name}
            </CardBody>
        </Card>
    )
}