import {RestClient} from "@/utils/rest-client";
import React from "react";
import styles from "@styles/mypage.module.css";
import ClientMyPagePoints from "@/components/mypage/client-mypage-points";
import {cookies} from "next/headers";

export default async function MyPagePoints(){
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/user/points?size=5`)
        .session(cookie)
        .fetch();
    const pageCursor = await response.json();

    return (
        <div className={styles.container}>
            <div className={styles.containerName}>포인트</div>
            <ClientMyPagePoints pageCursor={pageCursor}/>
        </div>
    );
}
