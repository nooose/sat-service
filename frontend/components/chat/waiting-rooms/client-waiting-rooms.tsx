"use client"

import React, {useEffect, useState} from "react";
import ClientCreateChatRoomButton from "@/components/chat/waiting-rooms/client-create-chat-room-button";
import ClientWaitingChatRoom from "@/components/chat/client-waiting-chat-room";
import {Client} from "@stomp/stompjs";
import {API_HOST} from "@/utils/rest-client";

export default function ClientWaitingRooms({chatRooms, memberId} : {
    chatRooms: ChatRoomResponse[],
    memberId: number
}) {
    const [chatRoomsState, setChatRoomsState] = useState<ChatRoomResponse[]>(chatRooms);

    useEffect(() => {
        const stompClient = new Client({
            brokerURL: `ws://${API_HOST}/chat`,
            reconnectDelay: 5000,
            debug: (str) => {
                console.log(new Date(), str);
            },
            onConnect: () => {
                stompClient.subscribe(`/topic/rooms`, (message) => {
                    const waitingRoomInformation: ChatRoomOccupancyQuery[] = JSON.parse(message.body);
                    const map = new Map<string, number>();
                    waitingRoomInformation.forEach((room: ChatRoomOccupancyQuery) => {
                        map.set(room.id, room.onlineMemberCount)
                    });
                    const rooms = chatRoomsState.map(room => {
                        const newChatRoom: ChatRoomResponse = {
                            id: room.id,
                            name: room.name,
                            maximumCapacity: room.maximumCapacity,
                            memberCount: map.get(room.id) ?? 0,
                            ownerId: room.ownerId,
                        }
                        return newChatRoom
                    })
                    setChatRoomsState(rooms)
                });

            },
            onStompError: (frame) => {
                console.error('error: ' + frame.headers['message']);
                console.error('error details: ' + frame.body);
            },
        });

        stompClient.activate();
        return () => {
            stompClient.deactivate()
                .then();
        };
    }, []);

    return (
        <div>
            <ClientCreateChatRoomButton/>
            {chatRoomsState.map((chatRoom: ChatRoomResponse) => (
                <ClientWaitingChatRoom
                    key={chatRoom.id}
                    chatRoom={chatRoom}
                    isOwner={memberId == chatRoom.ownerId}
                />
            ))}
        </div>
    )
}