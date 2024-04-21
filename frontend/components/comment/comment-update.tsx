"use client"

import {Textarea} from "@nextui-org/input";
import {Button, Card} from "@nextui-org/react";
import React from "react";
import {useRouter} from "next/navigation";
import {put} from "@/utils/client";
import CommentUpdateRequest from "@/model/dto/request/CommentUpdateRequest";
import commentUpdateStore from "@/store/comment-update-store";
import styles from "@styles/comment.module.css";

export default ({commentId, articleId, onSuccess}: {
    commentId: number,
    articleId: number,
    onSuccess: () => void
}) => {
    const state = commentUpdateStore((state: any) => state);
    const router = useRouter();

    function saveComment(commentUpdateRequest: CommentUpdateRequest) {
        return put(`/board/comments/${commentId}`, commentUpdateRequest);
    }

    return (
        <div className={styles.requestContainer}>
            <Card className={styles.requestCardContainer}>
                <Textarea
                    variant="underlined"
                    label="댓글 수정"
                    labelPlacement="outside"
                    placeholder="내용을 입력해 주세요"
                    onChange={event => state.setContent(event.target.value)}
                />
                <Button className={styles.requestButtonContainer} color="primary" size="md"
                        onClick={
                            () => {
                                const request: CommentUpdateRequest = {
                                    content: state.content,
                                }
                                saveComment(request)
                                    .then(response => {
                                        if (response.ok) {
                                            onSuccess();
                                            router.push(`/articles/${articleId}`);
                                        }
                                    })
                                    .catch(error => {
                                        console.error('API 요청 중 오류가 발생하였습니다:', error);
                                    });
                        }
                }>수정</Button>
            </Card>
        </div>
    )
};