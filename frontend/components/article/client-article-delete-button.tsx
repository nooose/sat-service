"use client"

import {Button, Image} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import deleteStyles from "@styles/delete-button.module.css";
import React from "react";
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";

export default function ClientArticleDeleteButton({id}: { id: number }) {
    const router = useRouter();
    const deleteEvent = () => {
        RestClient.delete(`/board/articles/${id}`)
            .successHandler(() => {
                router.push('/')
            })
            .errorHandler(error => {
                if (error.status == 404) {
                    errorToast("삭제에 실패하였습니다. (존재하지 않는 게시글)");
                }
            })
            .fetch();
    }

    return (
        <div>
            <button className={deleteStyles.deleteButton} onClick={deleteEvent}>
                <Image src={"/x-button.svg"} alt="Delete"/>
            </button>
        </div>
    );
}
