import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import React from "react";
import styles from "@styles/mypage.module.css";
import ClientMyPageComments from "@/components/mypage/client-mypage-comments";

export default async function MyPageComments() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/user/comments?size=5`)
        .session(cookie)
        .fetch();
    const pageCursor = await response.json();

    return (
        <div className={styles.container}>
            <div className={styles.containerName}>댓글</div>
            <ClientMyPageComments pageCursor={pageCursor}/>
        </div>
    )
};

