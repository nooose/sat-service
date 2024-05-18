import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import MypageArticle from "@/components/mypage/mypage-article";
import React from "react";
import styles from "@styles/mypage.module.css";

async function getArticles() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/user/articles")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function MyPageArticles() {
    const articles = await getArticles();
    return (
        <div className={styles.container}>
            <div className={styles.containerName}>게시글</div>
            {articles.map((article: ArticleSimpleResponse) => (
                <MypageArticle key={article.id} article={article}/>
            ))}
        </div>
    );
}
