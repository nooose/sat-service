import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import {getUserInfo} from "@/components/user-login";
import React from "react";
import styles from "@styles/mypage.module.css";
import MypagePoint from "@/components/mypage/mypage-point";
import MyPointResponse from "@/model/dto/response/MyPointResponse";

async function getPoints(memberId: number) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/user/points")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function MyPagePoints() {
    const cookie = cookies().get("JSESSIONID")?.value
    const member = await getUserInfo(cookie);
    const points = await getPoints(member.id);
    return (
        <div className={styles.container}>
            <div className={styles.containerName}>게시글</div>
            {points.map((point: MyPointResponse) => (
                <MypagePoint key={point.id} point={point}/>
            ))}
        </div>
    );
}
