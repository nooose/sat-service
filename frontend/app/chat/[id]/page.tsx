"use client"

import React, {useEffect, useState} from "react";
import {Client} from "@stomp/stompjs";
import ClientChatRoom from "@/components/chat/client-chat-room";
import {API_HOST, RestClient} from "@/utils/rest-client";
import {Button, Listbox, ListboxItem} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export default function Chat({params: {id}}: any) {
    const [client, setClient] = useState<Client>();
    const [messages, setMessages] = useState<ChatMessageResponse[]>([]);
    const [activeUsers, setActiveUsers] = useState<ChatUser[]>([]);
    const router = useRouter();

    useEffect(() => {
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

                stompClient.subscribe(`/topic/rooms/${id}/active-users`, (message) => {
                    console.log("입장/퇴장 메시지");
                    console.log(message.body);
                    const body: ChatUser[] = JSON.parse(message.body);
                    setActiveUsers(body);
                });
            },
            onStompError: (frame) => {
                console.error('error: ' + frame.headers['message']);
                console.error('error details: ' + frame.body);
            },
        });

        const response= RestClient.get(`/chat/rooms/${id}/messages`).fetch();
        const messages: Promise<ChatMessageResponse[]> = response.then(response => response.json());
        messages.then(messages => {
            setMessages(messages);
        });

        stompClient.activate();
        setClient(stompClient);

        return () => {
            stompClient.deactivate()
                .then();
        };
    }, []);

    return (
        <div>
            <div className="flex justify-center">
                <ClientChatRoom
                    chatRoomId={id}
                    messages={messages}
                    client={client}
                />
                <aside>
                    <h1>참여인원</h1>
                    <div
                        className="w-full max-w-[260px] border-small px-1 py-2 rounded-small border-default-200 dark:border-default-100">
                        <Listbox
                            classNames={{
                                base: "max-w-xs",
                                list: "max-h-[300px] overflow-scroll",
                            }}
                            items={activeUsers}
                            emptyContent={""}
                            variant="flat"
                        >
                            {(user) => (
                                <ListboxItem key={user.sessionId} textValue={user.name}>
                                    <div className="flex gap-2 items-center">
                                        <div className="flex flex-col">
                                            <span className="text-small">{user.name}</span>
                                        </div>
                                    </div>
                                </ListboxItem>
                            )}
                        </Listbox>
                    </div>
                </aside>
            </div>
            <div>
                <Button color="danger"
                        onClick={event => {
                            router.push("/chat");
                            router.refresh();
                        }}
                >
                    나가기
                </Button>
            </div>
        </div>
    );
}