"use client"

import React, {useState} from "react";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {RestClient} from "@/utils/rest-client";
import {useRouter} from "next/navigation";
import ChatCreateRequest from "@/model/dto/request/ChatCreateRequest";

const MIN_TITLE_LENGTH = 2;
const MAX_TITLE_LENGTH = 20;

export default function CreateChatRoomPage() {
    const router = useRouter();
    const [title, setTitle] = useState("");

    const isValidateTitle = () => {
        return title.length >= MIN_TITLE_LENGTH || title.length <= MAX_TITLE_LENGTH;
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
                color={isValidateTitle() ? 'primary' : "default"}
                disabled={!isValidateTitle()}
                onClick={createChatRoom}>
                생성
            </Button>
        </div>
    );
}