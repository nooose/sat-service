"use client"

import React, {useEffect, useRef, useState} from "react";
import {Client} from "@stomp/stompjs";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import ChatMessage from "@/components/chat/chat-message";
import {ScrollShadow} from "@nextui-org/scroll-shadow";
import {RestClient} from "@/utils/rest-client";
import SatUser from "@/model/domain/SatUser";

export default function ClientChatRoom(
    {client, chatRoomId, messages}: { client: Client | undefined, chatRoomId: string, messages: ChatMessageResponse[] }
) {
    const [input, setInput] = useState("");
    const [memberId, setMemberId] = useState(0);
    const scrollRef = useRef<HTMLDivElement>(null);

    const setUserId = async () => {
        const response = await RestClient.get(`/user/members/me`).fetch();
        const satUser: SatUser = await response.json();
        setMemberId(satUser.id)
    }

    useEffect(() => {
        setUserId();
    }, []);

    useEffect(() => {
        if (scrollRef.current) {
            scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
        }
    }, [messages]);

    const sendMessage = () => {
        const inputMessage = input.trim();
        if (inputMessage.length == 0) {
            return
        }
        const requestMessage: ChatMessageRequest = {
            content: inputMessage,
        };
        client!!.publish({
            destination: `/chat/rooms/${chatRoomId}`,
            body: JSON.stringify(requestMessage),
        });
        setInput('');
    };

    return (
        <div>
            <ScrollShadow ref={scrollRef} className="h-[500px]">
                {messages.map((message, index) => (
                    <ChatMessage
                        key={index}
                        isOwner={message.senderId === memberId}
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
        </div>
    );
}
