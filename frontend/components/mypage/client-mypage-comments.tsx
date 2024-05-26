"use client"

import {RestClient} from "@/utils/rest-client";
import React, {useCallback, useRef, useState} from "react";
import {Button} from "@nextui-org/react";
import PageCursor from "@/model/dto/response/PageCursor";
import MyPageCommentResponse from "@/model/dto/response/MyPageCommentResponse";
import MypageComment from "@/components/mypage/mypage-comment";
import styles from "@styles/mypage.module.css"

export default function ClientMyPageComments({pageCursor}: { pageCursor: PageCursor<MyPageCommentResponse[]> }) {
    const [comments, setComments] = useState<MyPageCommentResponse[]>(pageCursor.data)
    const [cursorId, setCursorId] = useState(pageCursor.nextCursor.id)

    const observer = useRef<IntersectionObserver | null>(null);
    const lastItemRef = useCallback((node: HTMLDivElement) => {
        if (observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver((entries) => {
            if (entries[0].isIntersecting) {
                console.log('마지막 스크롤');
            }
        });
        if (node) observer.current.observe(node);
    }, []);

    const fetchComments = async () => {
        const response = await RestClient.get(`/user/comments?id=${cursorId}&size=5`).fetch();
        const pageResponse: PageCursor<MyPageCommentResponse[]> = await response.json();
        setCursorId(pageResponse.nextCursor.id)
        setComments((likes) => [...likes, ...pageResponse.data]);
    }

    return (
        <>
            {comments.map((comment) => (
                <MypageComment key={comment.id} comment={comment}/>
            ))}
            <div ref={lastItemRef}></div>
            <Button className={styles.moreButton}
                    color={"success"}
                onClick={event => fetchComments()}
            >더 보기</Button>
        </>
    );
}
