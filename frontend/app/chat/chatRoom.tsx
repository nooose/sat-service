"use client"

import React, {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import ChatMessage from "@/model/dto/request/ChatMessage";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {v4 as uuidv4} from "uuid";

export default function ChatRoom(
    {client, messages}: { client: Client | undefined, messages: ChatMessage[] }
) {
    const [input, setInput] = useState("");
    const [userId, setUserId] = useState("")

    useEffect(() => {
        setUserId(uuidv4());
    }, [])

    const sendMessage = () => {
        const inputMessage = input.trim();
        if (inputMessage.length == 0) {
            return
        }

        const requestMessage: ChatMessage = {
            senderId: userId,
            text: inputMessage,
        };
        setInput('');
        client!!.publish({
            destination: '/app/message/1000',
            body: JSON.stringify(requestMessage),
        });
    };

    return (
        <div>
            <h1>채팅 목록</h1>

            {messages.map((message, index) => (
                <div key={index}>
                    보낸사람: {message.senderId}: {message.text}
                </div>
            ))}

            <Input
                type="text"
                label="메시지"
                placeholder="메시지"
                value={input}
                onChange={(e) => {
                    setInput(e.target.value);
                }}
                onKeyUp={(e) => {
                    e.key === "Enter" && sendMessage();
                }}
            />
            <Button
                color={"primary"}
                onClick={sendMessage}>
                보내기
            </Button>
        </div>);
}