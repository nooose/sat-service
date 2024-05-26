import {RestClient} from "@/utils/rest-client";
import React from "react";
import styles from "@styles/mypage.module.css";
import {cookies} from "next/headers";
import ClientMyPageLikes from "@/components/mypage/client-mypage-likes";

export default async function MyPageLikes() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/user/likes?size=5`)
        .session(cookie)
        .fetch();
    const pageCursor = await response.json();

    return (
        <div className={styles.container}>
            <div className={styles.containerName}>좋아요 누른 게시글</div>
            <ClientMyPageLikes pageCursor={pageCursor}/>
        </div>
    );
}
