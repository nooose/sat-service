"use client"

import {Button, Card, CardBody, CardHeader, Divider, Image} from "@nextui-org/react";
import React, {useState} from "react";
import CommentResponse from "@/model/dto/response/CommentResponse";
import CommentWrite from "@/components/comment/comment-write";
import styles from "@styles/comment.module.css";
import CommentUpdate from "@/components/comment/comment-update";
import {Comment} from "postcss";

export default function Comment({articleId, comment, loginUserId}: {articleId: number, comment: CommentResponse, loginUserId: number | undefined | null}) {
    const [isUpdateOpen, setIsUpdateOpen] = useState(false);
    const [isReplyOpen, setIsReplyOpen] = useState(false);
    const updateToggleAccordion = () => {
        setIsReplyOpen(false);
        setIsUpdateOpen(!isUpdateOpen);
    }

    const replyToggleAccordion = () => {
        setIsUpdateOpen(false);
        setIsReplyOpen(!isReplyOpen);
    }

    return (
        <div className={styles.container}>
            <Card className={styles.cardContainer} key={comment.id}>
                <CardHeader className="flex items-center justify-between">
                    <div className="flex items-center gap-3">
                        <Image
                            alt="nextui logo"
                            height={40}
                            radius="sm"
                            src="https://avatars.githubusercontent.com/u/86160567?s=200&v=4"
                            width={40}
                        />
                        <div className="flex flex-col">
                            <p>{comment.memberName}</p>
                        </div>
                    </div>
                    <div>
                        {loginUserId === comment.memberId ? <Button className={styles.commentButton} size={"sm"} color={"default"} onClick={updateToggleAccordion}>수정</Button> : ''}
                        <Button className={styles.commentButton} size={"sm"} color={"primary"} onClick={replyToggleAccordion}>대댓글</Button>
                    </div>
                </CardHeader>
                <Divider/>
                <CardBody>
                    <p className="flex gap-10 items-center">
                        {comment.content}
                    </p>
                </CardBody>
                <Divider/>
            </Card>
            {isUpdateOpen && <CommentUpdate articleId={articleId} commentId={comment.id}/>}
            {isReplyOpen && <CommentWrite articleId={articleId} parentId={comment.id}/>}

            {comment.children?.map((comment: CommentResponse) => (
                <div className={styles.childrenContainer}>
                    <Comment articleId={articleId} comment={comment} loginUserId={loginUserId} key={comment.id}/>
                </div>
            ))}
        </div>
    );
};