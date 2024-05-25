"use client"

import {RestClient} from "@/utils/rest-client";
import React, {useEffect, useState} from "react";
import styles from "@styles/mypage.module.css";
import LikedArticleResponse from "@/model/dto/response/LikedArticleResponse";
import MypageLike from "@/components/mypage/mypage-like";
import PageCursor from "@/model/dto/response/PageCursor";
import {Button} from "@nextui-org/react";

export default function MyPageLikes() {
    const [likes, setLikes] = useState<LikedArticleResponse[]>([])
    const [cursorId, setCursorId] = useState<string>("")

    const fetchLikes = async () => {
        const response = await RestClient.get(`/user/likes?id=${cursorId}&size=10`)
            .fetch();
        const pageResponse: PageCursor<LikedArticleResponse[]> = await response.json();
        setCursorId(pageResponse.nextCursor.id?.toString() ?? "")
        setLikes((likes) => [...likes, ...pageResponse.data]);
    }

    useEffect(() => {
        fetchLikes();
    }, []);


    return (
        <div className={styles.container}>
            <div className={styles.containerName}>좋아요 누른 게시글</div>
            {likes.map((like: LikedArticleResponse) => (
                <MypageLike like={like}/>
            ))}
            <Button
                onClick={event => fetchLikes()}
            >더 보기</Button>
        </div>
    );
}
