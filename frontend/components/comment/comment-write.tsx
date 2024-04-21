"use client"

import {Textarea} from "@nextui-org/input";
import {Button, Card} from "@nextui-org/react";
import React from "react";
import commentCreateStore from "@/store/comment-create-store";
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import {useRouter} from "next/navigation";
import {post} from "@/utils/client";
import styles from "@/styles/comment.module.css"

export default ({articleId, parentId}: {
    articleId: number,
    parentId?: number | null | undefined,
}) => {
    const state = commentCreateStore((state: any) => state);
    const router = useRouter();

    function saveComment(commentCreateRequest: CommentCreateRequest) {
        return post(`/board/articles/${articleId}/comments`, commentCreateRequest);
    }

    return (
        <div className={styles.requestContainer}>
            <Card className={styles.requestCardContainer}>
                <Textarea
                    variant="underlined"
                    label="댓글 작성"
                    labelPlacement="outside"
                    placeholder="내용을 입력해 주세요"
                    onChange={event => state.setContent(event.target.value)}
                />
                <Button className={styles.requestButtonContainer} color="primary" size="md"
                        onClick={() => {
                            const request: CommentCreateRequest = {
                                content: state.content,
                                parentId: parentId,
                            }
                            saveComment(request)
                                .then(response => {
                                    if (response.ok) {
                                        router.push(`/articles/${articleId}`);
                                    }
                                })
                                .catch(error => {
                                    console.error('API 요청 중 오류가 발생하였습니다:', error);
                                });
                        }}>등록</Button>
            </Card>
        </div>
    )
};