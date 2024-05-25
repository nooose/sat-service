"use client"

import {RestClient} from "@/utils/rest-client";
import React, {useState} from "react";
import {Button} from "@nextui-org/react";
import PageCursor from "@/model/dto/response/PageCursor";
import LikedArticleResponse from "@/model/dto/response/LikedArticleResponse";
import MypageLike from "@/components/mypage/mypage-like";

export default function ClientMyPageLikes({pageCursor}: { pageCursor: PageCursor<LikedArticleResponse[]> }) {
    const [likes, setLikes] = useState<LikedArticleResponse[]>(pageCursor.data)
    const [cursorId, setCursorId] = useState(pageCursor.nextCursor.id)

    const fetchLikes = async () => {
        const response = await RestClient.get(`/user/likes?id=${cursorId}&size=5`).fetch();
        const pageResponse: PageCursor<LikedArticleResponse[]> = await response.json();
        setCursorId(pageResponse.nextCursor.id)
        setLikes((likes) => [...likes, ...pageResponse.data]);
    }

    return (
        <>
            {likes.map((like) => (
                <MypageLike key={like.id} like={like}/>
            ))}
            <Button
                onClick={event => fetchLikes()}
            >더 보기</Button>
        </>
    );
}
