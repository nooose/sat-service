"use client"

import {Textarea} from "@nextui-org/input";
import {Button, Card} from "@nextui-org/react";
import React, {useState} from "react";
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import {useRouter} from "next/navigation";
import {post} from "@/utils/client";
import styles from "@/styles/comment.module.css"

export default ({articleId, parentId, setIsReplyOpen}: {
    articleId: number,
    parentId?: number | null | undefined,
    setIsReplyOpen: (isOpen: boolean) => void,
}) => {
    const [content, setContent] = useState('')
    const router = useRouter();

    function saveComment(commentCreateRequest: CommentCreateRequest) {
        return post(`/board/articles/${articleId}/comments`, commentCreateRequest);
    }

    return (
        <div className={styles.requestContainer}>
            <Card className={styles.requestCardContainer}>
                <Textarea
                    variant="faded"
                    label="댓글 작성"
                    labelPlacement="outside"
                    placeholder="내용을 입력해 주세요"
                    onChange={event => setContent(event.target.value)}
                />
                <Button className={styles.createButtonContainer} color="primary" size="md"
                        onClick={() => {
                            const request: CommentCreateRequest = {
                                content: content,
                                parentId: parentId,
                            }
                            saveComment(request)
                                .then(response => {
                                    if (response.ok) {
                                        setIsReplyOpen(false);
                                        router.push(`/articles/${articleId}`);
                                    }
                                })
                                .catch(error => {
                                    console.error('API 요청 중 오류가 발생하였습니다:', error);
                                });
                        }}>등록
                </Button>
            </Card>
        </div>
    )
};