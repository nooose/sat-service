import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import React from "react";
import styles from "@styles/mypage.module.css";
import MypagePoint from "@/components/mypage/mypage-point";
import MyPointResponse from "@/model/dto/response/MyPointResponse";

async function getPoints() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/user/points")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function MyPagePoints() {
    const points = await getPoints();
    return (
        <div className={styles.container}>
            <div className={styles.containerName}>포인트</div>
            {points.map((point: MyPointResponse) => (
                <MypagePoint key={point.id} point={point}/>
            ))}
        </div>
    );
}
