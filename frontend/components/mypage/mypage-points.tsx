"use client"

import {RestClient} from "@/utils/rest-client";
import React, {useEffect, useState} from "react";
import styles from "@styles/mypage.module.css";
import MypagePoint from "@/components/mypage/mypage-point";
import MyPointResponse from "@/model/dto/response/MyPointResponse";
import {Button} from "@nextui-org/react";
import PageCursor from "@/model/dto/response/PageCursor";

export default function MyPagePoints(){
    const [points, setPoints] = useState<MyPointResponse[]>([])
    const [cursorId, setCursorId] = useState<string>("")

    const fetchPoints = async () => {
        const response = await RestClient.get(`/user/points?id=${cursorId}&size=10`).fetch();
        const pageResponse: PageCursor<MyPointResponse[]> = await response.json();
        setCursorId(pageResponse.nextCursor.id?.toString() ?? "")
        setPoints((points) => [...points, ...pageResponse.data]);
    }

    useEffect(() => {
        fetchPoints();
    }, []);

    return (
        <div className={styles.container}>
            <div className={styles.containerName}>포인트</div>
            {points.map((point: MyPointResponse) => (
                <MypagePoint key={point.id} point={point}/>
            ))}
            <Button
                onClick={event => fetchPoints()}
            >더 보기</Button>
        </div>
    );
}
