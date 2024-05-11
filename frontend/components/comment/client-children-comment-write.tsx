"use client"

import {Textarea} from "@nextui-org/input";
import {Button, Card} from "@nextui-org/react";
import React, {useState} from "react";
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import {useRouter} from "next/navigation";
import styles from "@/styles/comment.module.css"
import {RestClient} from "@/utils/rest-client";

export default function ChildCommentWrite(
    {articleId, parentId, setIsReplyOpen}
        :
    {articleId: number, parentId?: number | null | undefined, setIsReplyOpen: (isReplyOpen: boolean) => void}
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
        <div className={styles.requestContainer}>
            <Card className={styles.requestCardContainer}>
                <Textarea
                    variant="faded"
                    label="댓글 작성"
                    labelPlacement="outside"
                    placeholder="내용을 입력해 주세요"
                    isInvalid={isContentError}
                    errorMessage={contentErrorMessage}
                    onChange={event => setChildContent(event.target.value)}
                />
                <Button className={styles.createButtonContainer} color="primary" size="md"
                        onClick={saveComment}>등록
                </Button>
            </Card>
        </div>
    )
};
