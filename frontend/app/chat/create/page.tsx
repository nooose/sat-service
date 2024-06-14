"use client"

import React, {useState} from "react";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {RestClient} from "@/utils/rest-client";
import {useRouter} from "next/navigation";
import ChatCreateRequest from "@/model/dto/request/ChatCreateRequest";

const MIN_TITLE_LENGTH = 2;
const MAX_TITLE_LENGTH = 20;
const MIN_HEADCOUNT = 2;
const MAX_HEADCOUNT = 20;


export default function CreateChatRoomPage() {
    const router = useRouter();
    const [name, setName] = useState("");
    const [maximumCapacity, setHeadcount] = useState("")

    const isValidateTitle = () => {
        return name.length >= MIN_TITLE_LENGTH && name.length <= MAX_TITLE_LENGTH;
    }

    const isValidateHeadcount = () => {
        const headcountNumber = parseInt(maximumCapacity, 10);
        return !isNaN(headcountNumber) && headcountNumber >= MIN_HEADCOUNT && headcountNumber <= MAX_HEADCOUNT;
    };

    const createChatRoom = () => {
        const request: ChatCreateRequest = {
            name: name,
            maximumCapacity: parseInt(maximumCapacity, 10),
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
                value={name}
                placeholder={'채팅방 이름(2글자 이상 20글자 이하)'}
                onChange={event => setName(event.target.value)}
            />
            <Input
                value={maximumCapacity}
                placeholder={'채팅방 인원수(2~20명)'}
                onChange={event => setHeadcount(event.target.value)}
            />
            <Button
                color={isValidateTitle() && isValidateHeadcount() ? 'primary' : "default"}
                disabled={!(isValidateTitle() && isValidateHeadcount())}
                onClick={createChatRoom}>
                생성
            </Button>
        </div>
    );
}
