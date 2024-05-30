"use client"

import {Button, Image, useDisclosure} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import deleteStyles from "@styles/delete-button.module.css";
import React from "react";
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";
import DeleteModal from "@/components/modal/delete-modal";

export default function ClientArticleDeleteButton({id}: { id: number }) {
    const {isOpen, onOpen, onOpenChange} = useDisclosure();
    const router = useRouter();
    const deleteEvent = () => {
        RestClient.delete(`/board/articles/${id}`)
            .successHandler(() => {
                router.push('/');
                router.refresh();
            })
            .errorHandler(error => {
                errorToast("존재하지 않는 게시글이므로 삭제에 실패하였습니다.");
            })
            .fetch();
    }


    return (
        <div>
            <button className={deleteStyles.deleteButton} onClick={onOpen}>
                <Image src={"/x-button.svg"} alt="Delete"/>
            </button>
            <DeleteModal
                isOpen={isOpen}
                onOpenChange={onOpenChange}
                deleteEvent={deleteEvent}
            />
        </div>
    );
}
