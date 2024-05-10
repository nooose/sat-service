"use client"

import {Textarea} from "@nextui-org/input";
import {Button, Card} from "@nextui-org/react";
import React, {useState} from "react";
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import {useRouter} from "next/navigation";
import styles from "@/styles/comment.module.css"
import {RestClient} from "@/utils/restClient";
import {Simulate} from "react-dom/test-utils";

export default function ClientCommentWrite(
    { articleId, parentId, }
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
                    value={content}
                    onChange={event => setContent(event.target.value)}
                />
                <Button className={styles.createButtonContainer} color="primary" size="md"
                        onClick={saveComment}>등록
                </Button>
            </Card>
        </div>
    )
};
