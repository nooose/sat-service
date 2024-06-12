import React from "react";
import ClientCreateChatRoomButton from "@/components/chat/waiting-rooms/client-create-chat-room-button";
import {RestClient} from "@/utils/rest-client";
import ChatRoomResponse from "@/model/dto/response/ChatRoomResponse";
import ClientWaitingChatRoom from "@/components/chat/client-waiting-chat-room";
import {cookies} from "next/headers";
import {getUserInfo} from "@/components/user-login";
import ClientWaitingRooms from "@/components/chat/waiting-rooms/client-waiting-rooms";

async function getChatRooms(cookie: string | undefined) {
    const response = await RestClient.get('/chat/rooms')
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function WaitingRooms() {
    const cookie = cookies().get("JSESSIONID")?.value
    const chatRooms: ChatRoomResponse[] = await getChatRooms(cookie);
    const userInfo = await getUserInfo(cookie);

    return (
        <div>
            <ClientWaitingRooms
                chatRooms={chatRooms}
                memberId={userInfo.id}
            />
        </div>
    );
}
