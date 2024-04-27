import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import ClientArticle from "@/components/article/client-article";
import {get} from "@/utils/client";
import {cookies} from "next/headers";

async function getArticles() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get("/board/articles", cookie);
    return await response.json();
}

export default async function Articles() {
    const articles = await getArticles();
    return (
        <div>
            {articles.map((article: ArticleSimpleResponse) => (
                <ClientArticle key={article.id} article={article}/>
            ))}
        </div>
    );
}
