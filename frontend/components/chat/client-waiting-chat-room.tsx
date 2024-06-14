"use client"

import {Card, CardBody, CardHeader, Image, useDisclosure} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import deleteStyles from "@styles/delete-button.module.css";
import DeleteModal from "@/components/modal/delete-modal";
import React, {useState} from "react";
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";

export default function ClientWaitingChatRoom({chatRoom, isOwner}: {
    chatRoom: ChatRoomResponse,
    isOwner: boolean,
}) {
    const router = useRouter();
    const goChatRoom = () => {
        router.push(`/chat/${chatRoom.id}`);
        router.refresh();
    }

    // TODO: 삭제 버튼 Wrapping
    const {isOpen, onOpen, onOpenChange} = useDisclosure();
    const deleteEvent = () => {
        RestClient.delete(`/chat/rooms/${chatRoom.id}`)
            .successHandler(() => {
                router.push('/chat');
                router.refresh();
            })
            .errorHandler(error => errorToast(error.errorMessage()))
            .fetch();
    }

    return (
        <Card>
            {isOwner &&
                <CardHeader>
                    <button className={deleteStyles.deleteButton} onClick={onOpen}>
                        <Image src={"/x-button.svg"} alt="Delete"/>
                    </button>
                    <DeleteModal
                        isOpen={isOpen}
                        onOpenChange={onOpenChange}
                        deleteEvent={deleteEvent}
                    />
                </CardHeader>
            }
            <CardBody onClick={goChatRoom}>
                <div style={{display: "flex", justifyContent: "space-between"}}>
                    <div>{chatRoom.name}</div>
                    <div>{chatRoom.memberCount ?? 0} / {chatRoom.maximumCapacity}</div>
                </div>
            </CardBody>
        </Card>
    )
}
