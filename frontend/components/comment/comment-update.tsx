"use client"

import {Textarea} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import React, {useState} from "react";
import {useRouter} from "next/navigation";
import {put} from "@/utils/client";
import CommentUpdateRequest from "@/model/dto/request/CommentUpdateRequest";
import styles from "@styles/comment.module.css";
import CommentResponse from "@/model/dto/response/CommentResponse";

export default ({articleId, comment, setIsUpdateOpen}: {
    articleId: number,
    comment: CommentResponse,
    setIsUpdateOpen: (isUpdateOpen: boolean) => void
}) => {
    const [content, setContent] = useState(comment.content)
    const router = useRouter();

    function saveComment(commentUpdateRequest: CommentUpdateRequest) {
        return put(`/board/comments/${comment.id}`, commentUpdateRequest);
    }

    return (
        <div className={styles.requestContainer}>
                <Textarea
                    variant="faded"
                    placeholder="내용을 입력해 주세요"
                    value={content}
                    onChange={event => setContent(event.target.value)}
                />
                <Button className={styles.updateButtonContainer} color="primary"
                        onClick={
                            () => {
                                const request: CommentUpdateRequest = {
                                    content: content,
                                }
                                saveComment(request)
                                    .then(response => {
                                        if (response.ok) {
                                            setIsUpdateOpen(false);
                                            router.push(`/articles/${articleId}`);
                                            router.refresh();
                                        }
                                    })
                                    .catch(error => {
                                        console.error('API 요청 중 오류가 발생하였습니다:', error);
                                    });
                        }
                }>수정</Button>
        </div>
    )
};