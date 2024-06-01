"use client"

import React, {useEffect, useRef, useState} from "react";
import {Client} from "@stomp/stompjs";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {v4 as uuidv4} from "uuid";
import ChatMessage from "@/app/chat/chat-message";
import {ScrollShadow} from "@nextui-org/scroll-shadow";

export default function ChatRoom(
    {client, messages}: { client: Client | undefined, messages: ChatMessageResponse[] }
) {
    const [input, setInput] = useState("");
    const [userId, setUserId] = useState("")
    const scrollRef = useRef<HTMLDivElement>(null);
    const chatRoomId = 1000 // FIXME: 임시 채팅방 아이디 할당

    useEffect(() => {
        if (scrollRef.current) {
            scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
        }
    }, [messages]);

    // FIXME: 임시 아이디 할당
    useEffect(() => {
        setUserId(uuidv4().substring(0, 4));
    }, []);

    const sendMessage = () => {
        const inputMessage = input.trim();
        if (inputMessage.length == 0) {
            return
        }
        const requestMessage: ChatMessageRequest = {
            senderId: userId,
            text: inputMessage,
        };
        setInput('');
        client!!.publish({
            destination: `/chat/rooms/${chatRoomId}`,
            body: JSON.stringify(requestMessage),
        });
    };

    return (
        <>
            <ScrollShadow ref={scrollRef} className="h-[500px]">
                {messages.map((message, index) => (
                    <ChatMessage
                        isOwner={message.senderId === userId}
                        chatMessage={message}>
                    </ChatMessage>
                ))}
            </ScrollShadow>
            <Input
                type="text"
                placeholder="보낼 메시지를 입력하세요."
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onKeyUp={(e) => e.key === "Enter" && sendMessage()}
            />
            <Button
                color={"primary"}
                onClick={sendMessage}>
                전송
            </Button>
        </>
    );
}