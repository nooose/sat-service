"use client"

import {Textarea} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import React, {useState} from "react";
import {useRouter} from "next/navigation";
import CommentUpdateRequest from "@/model/dto/request/CommentUpdateRequest";
import styles from "@styles/comment.module.css";
import CommentResponse from "@/model/dto/response/CommentResponse";
import {RestClient} from "@/utils/restClient";

export default ({articleId, comment, setIsUpdateOpen}: {
    articleId: number,
    comment: CommentResponse,
    setIsUpdateOpen: (isUpdateOpen: boolean) => void
}) => {
    const [content, setContent] = useState(comment.content)
    const router = useRouter();

    const [isContentError, setIsContentError] = useState(false);
    const [contentErrorMessage, setContentErrorMessage] = useState("");

    const saveComment = () => {
        const request: CommentUpdateRequest = {
            content: content,
        }

        RestClient.put(`/board/comments/${comment.id}`)
            .requestBody(request)
            .successHandler(() => {
                setIsUpdateOpen(false);
                router.push(`/articles/${articleId}`);
                router.refresh();
            })
            .errorHandler(error => {
                const contentError = error.filedErrorMessage("content");
                setIsContentError(!!contentError);
                setContentErrorMessage(contentError);
                return;
            })
            .fetch();
    }

    return (
        <div className={styles.requestContainer}>
            <Textarea
                variant="faded"
                placeholder="내용을 입력해 주세요"
                value={content}
                isInvalid={isContentError}
                errorMessage={contentErrorMessage}
                onChange={event => setContent(event.target.value)}
            />
            <Button
                className={styles.updateButtonContainer} color="primary"
                onClick={saveComment}
            >수정</Button>
        </div>
    )
};