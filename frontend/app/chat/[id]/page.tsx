"use client"

import {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import ClientChatRoom from "@/components/chat/client-chat-room";
import {API_HOST} from "@/utils/rest-client";

export default function Chat({params: {id}}: any) {
    const [client, setClient] = useState<Client>();
    const [messages, setMessages] = useState<ChatMessageResponse[]>([]);

    useEffect(() => {
        console.log(id);
        const stompClient = new Client({
            brokerURL: `ws://${API_HOST}/chat`,
            reconnectDelay: 5000,
            debug: (str) => {
                console.log(new Date(), str);
            },
            onConnect: () => {
                console.log('Connected to server');
                stompClient.subscribe(`/topic/rooms/${id}`, (message) => {
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
        <ClientChatRoom
            chatRoomId={id}
            messages={messages}
            client={client}
        />
    );
}