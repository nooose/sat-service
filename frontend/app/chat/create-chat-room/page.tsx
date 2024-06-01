"use client"

import React, {useState} from "react";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {RestClient} from "@/utils/rest-client";
import {useRouter} from "next/navigation";
import ChatCreateRequest from "@/model/dto/request/ChatCreateRequest";

const MIN = 1;
const MAX = 21;

export default function CreateChatRoomPage() {
    const router = useRouter();
    const [title, setTitle] = useState("");
    const validate = () => {
        return title.length > MIN && title.length < MAX;
    }

    const createChatRoom = () => {
        const request: ChatCreateRequest = {
            name: title,
        }

        RestClient.post('/chat/rooms')
            .requestBody(request)
            .successHandler(() => {
                router.push("/chat");
                router.refresh();
            }).fetch();
    }

    return (
        <div>
            <h1>
                채팅방 생성
            </h1>
            <Input
                value={title}
                placeholder={'채팅방 이름'}
                onChange={event => setTitle(event.target.value)}
            />
            <Button
                color={validate() ? 'primary' : "default"}
                disabled={!validate()}
                onClick={createChatRoom}>
                생성
            </Button>
        </div>
    );
}