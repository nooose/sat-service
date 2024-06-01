"use client"

import {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import ChatMessage from "@/model/dto/request/ChatMessage";
import ChatRoom from "@/app/chat/chatRoom";

export default function Chat() {
    const [client, setClient] = useState<Client>();
    const [messages, setMessages] = useState<ChatMessage[]>([]);

    useEffect(() => {
        const chatRoomId = "1000";
        const stompClient = new Client({
            brokerURL: 'ws://www.sat.com:8080/chat',
            reconnectDelay: 5000,
            debug: (str) => {
                console.log(new Date(), str);
            },
            onConnect: () => {
                console.log('Connected to server');
                stompClient.subscribe(`/topic/${chatRoomId}`, (message) => {
                    const body = JSON.parse(message.body)
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