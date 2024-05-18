import CommentResponse from "@/model/dto/response/CommentResponse";
import ClientComment from "@/components/comment/client-comment";
import {cookies} from "next/headers";
import {getUserInfo} from "@/components/user-login";
import {RestClient} from "@/utils/rest-client";
import ClientCommentWrite from "@/components/comment/client-comment-write";
import React from "react";
import styles from "@/styles/comment.module.css"
import {Divider} from "@nextui-org/react";

async function getComments(articleId: number) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/board/articles/${articleId}/comments`)
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function Comments({articleId}: { articleId: number }) {
    const cookie = cookies().get("JSESSIONID")?.value
    const user = await getUserInfo(cookie);
    const comments = await getComments(articleId);

    return (
        <div className={styles.container}>
            <ClientCommentWrite articleId={articleId}/>
            <Divider className="my-4"/>

            {comments?.map((comment: CommentResponse) => (
                <ClientComment
                    articleId={articleId}
                    comment={comment}
                    loginUserId={user.id}/>
            ))}
        </div>
    )
};

