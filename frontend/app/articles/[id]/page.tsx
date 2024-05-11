import ArticleInfo, {getArticle} from "@/components/article/article-info";
import Comments from "@/components/comment/comments";
import React from "react";

export async function generateMetadata({params: {id}}: any) {
    const article = await getArticle(id)
    return {
        title: article.title,
    };
}

export default async function ArticlePage({params: {id}}: any) {
    return (
        <div>
            <ArticleInfo id={id}/>
            <Comments articleId={id}/>
        </div>
    );
}
