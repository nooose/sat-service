import ArticleInfo, {getArticle} from "@/components/article/article-info";
import Comments from "@/components/comment/comments";
import React, {Suspense} from "react";
import CommentWrite from "@/components/comment/comment-write";

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
            <Suspense>
                <Comments articleId={id}/>
            </Suspense>
        </div>
    );
}
