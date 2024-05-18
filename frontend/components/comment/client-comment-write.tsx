"use client"

import {Textarea} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import React, {useState} from "react";
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import {useRouter} from "next/navigation";
import styles from "@/styles/comment.module.css"
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";

export default function ClientCommentWrite(
    {articleId, parentId,}
        :
        { articleId: number, parentId?: number | null | undefined, }
) {
    const [content, setContent] = useState('')
    const [isContentError, setIsContentError] = useState(false);
    const [contentErrorMessage, setContentErrorMessage] = useState("");

    const router = useRouter();

    const saveComment = () => {
        const request: CommentCreateRequest = {
            content: content,
            parentId: parentId,
        }
        RestClient.post(`/board/articles/${articleId}/comments`)
            .requestBody(request)
            .successHandler(() => {
                setContent("");
                router.push(`/articles/${articleId}`);
                router.refresh();
            })
            .errorHandler(error => {
                if (error.status == 401) {
                    errorToast("로그인 후 이용해 주세요.");
                    return;
                }

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
                value={content}
                onChange={event => setContent(event.target.value)}
            />
            <Button className={styles.requestCommentButton}
                    color="primary"
                    size="sm"
                    onClick={saveComment}>등록
            </Button>
        </div>
    )
};
