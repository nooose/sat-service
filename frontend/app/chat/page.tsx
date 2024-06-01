"use client"

import {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import ChatRoom from "@/app/chat/chat-room";
import {API_HOST} from "@/utils/rest-client";

export default function Chat() {
    const [client, setClient] = useState<Client>();
    const [messages, setMessages] = useState<ChatMessageResponse[]>([]);

    useEffect(() => {
        const chatRoomId = "1000";
        const stompClient = new Client({
            brokerURL: `ws://${API_HOST}/chat`,
            reconnectDelay: 5000,
            debug: (str) => {
                console.log(new Date(), str);
            },
            onConnect: () => {
                console.log('Connected to server');
                stompClient.subscribe(`/topic/rooms/${chatRoomId}`, (message) => {
                    const body: ChatMessageResponse = JSON.parse(message.body)
                    setMessages(prevMessages => [...prevMessages, body]);
                });
            },
            onStompError: (frame) => {
                console.error('error: ' + frame.headers['message']);
                console.error('error details: ' + frame.body);
            },
        });

        stompClient.activate();
        setClient(stompClient);

        return () => {
            stompClient.deactivate()
                .then();
        };
    }, []);

    return (
        <ChatRoom
            messages={messages}
            client={client}
        />
    );
}