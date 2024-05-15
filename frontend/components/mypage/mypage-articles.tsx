import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import ClientMyPageArticle from "@/components/mypage/client-mypage-article";
import {getUserInfo} from "@/components/user-login";
import React from "react";
import styles from "@styles/mypage.module.css";

async function getArticles(memberId: number) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/user/articles")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function MyPageArticles() {
    const cookie = cookies().get("JSESSIONID")?.value
    const member = await getUserInfo(cookie);
    const articles = await getArticles(member.id);
    return (
        <div className={styles.container}>
            <div className={styles.containerName}>게시글</div>
            {articles.map((article: ArticleSimpleResponse) => (
                <ClientMyPageArticle key={article.id} article={article}/>
            ))}
        </div>
    );
}
