import ArticleSimpleResponse from "@/model/response/ArticleSimpleResponse";
import Article from "@/components/article/article";
import {get} from "@/utils/client";
import {cookies, headers} from "next/headers";

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
                <Article key={article.id} article={article}/>
            ))}
        </div>
    );
}
