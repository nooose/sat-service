"use client"

import {RestClient} from "@/utils/rest-client";
import React, {useState} from "react";
import MypagePoint from "@/components/mypage/mypage-point";
import MyPointResponse from "@/model/dto/response/MyPointResponse";
import {Button} from "@nextui-org/react";
import PageCursor from "@/model/dto/response/PageCursor";
import styles from "@styles/mypage.module.css";

export default function ClientMyPagePoints({pageCursor}: { pageCursor: PageCursor<MyPointResponse[]> }) {
    const [points, setPoints] = useState<MyPointResponse[]>(pageCursor.data)
    const [cursorId, setCursorId] = useState(pageCursor.nextCursor.id)

    const fetchPoints = async () => {
        const response = await RestClient.get(`/user/points?id=${cursorId}&size=5`).fetch();
        const pageResponse: PageCursor<MyPointResponse[]> = await response.json();
        setCursorId(pageResponse.nextCursor.id)
        setPoints((points) => [...points, ...pageResponse.data]);
    }

    return (
        <>
            {points.map((point: MyPointResponse) => (
                <MypagePoint key={point.id} point={point}/>
            ))}
            <Button className={styles.moreButton}
                color={"success"}
                onClick={event => fetchPoints()}
            >더 보기</Button>
        </>
    );
}
