import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import React from "react";
import MyPageClientComment from "@/components/mypage/client-mypage-comment";
import MyPageCommentResponse from "@/model/dto/response/MyPageCommentResponse";
import styles from "@styles/mypage.module.css";

async function getComments() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/user/comments`)
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function MyPageComments() {
    const comments = await getComments();

    return (
        <div className={styles.container}>
            <div className={styles.containerName}>댓글</div>
            {comments?.map((comment: MyPageCommentResponse) => (
                <MyPageClientComment comment={comment}/>
            ))}
        </div>
    )
};

