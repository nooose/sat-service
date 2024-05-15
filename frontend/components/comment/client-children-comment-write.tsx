"use client"

import {Textarea} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import React, {useState} from "react";
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import {useRouter} from "next/navigation";
import styles from "@/styles/comment.module.css"
import {RestClient} from "@/utils/rest-client";

export default function ChildCommentWrite(
    {articleId, parentId, setIsReplyOpen}
        :
        { articleId: number, parentId?: number | null | undefined, setIsReplyOpen: (isReplyOpen: boolean) => void }
) {
    const [childContent, setChildContent] = useState('')
    const [isContentError, setIsContentError] = useState(false);
    const [contentErrorMessage, setContentErrorMessage] = useState("");

    const router = useRouter();

    const saveComment = () => {
        const request: CommentCreateRequest = {
            content: childContent,
            parentId: parentId,
        }
        RestClient.post(`/board/articles/${articleId}/comments`)
            .requestBody(request)
            .successHandler(() => {
                setIsReplyOpen(false)
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
        <div className={styles.requestComment}>
            <Textarea
                variant="faded"
                placeholder="댓글 내용"
                isInvalid={isContentError}
                errorMessage={contentErrorMessage}
                onChange={event => setChildContent(event.target.value)}
            />
            <Button className={styles.requestCommentButton}
                    color="primary"
                    size="sm"
                    onClick={saveComment}>등록
            </Button>
        </div>
    )
};
