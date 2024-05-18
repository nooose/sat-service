import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import React from "react";
import styles from "@styles/mypage.module.css";
import LikedArticleResponse from "@/model/dto/response/LikedArticleResponse";
import MypageLike from "@/components/mypage/mypage-like";

async function getLikes() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/user/likes")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function MyPageLikes() {
    const likes = await getLikes();
    return (
        <div className={styles.container}>
            <div className={styles.containerName}>좋아요 누른 게시글</div>
            {likes.map((like: LikedArticleResponse) => (
                <MypageLike like={like}/>
            ))}
        </div>
    );
}
