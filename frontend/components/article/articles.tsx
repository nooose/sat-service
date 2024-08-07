import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import ClientArticle from "@/components/article/client-article";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";

async function getArticles() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/board/articles")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function Articles() {
    const articles = await getArticles();
    console.log(articles);
    return (
        <div>
            {articles.map((article: ArticleSimpleResponse) => (
                <ClientArticle key={article.id} article={article}/>
            ))}
        </div>
    );
}
