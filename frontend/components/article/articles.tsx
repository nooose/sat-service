import ArticleSimpleResponse from "@/model/response/ArticleSimpleResponse";
import Article from "@/components/article/article";
import {httpClient} from "@/utils/client";

async function getArticles() {
    const response = await httpClient("/board/articles");
    return await response.json();
}

export default async function Articles() {
    const articles = await getArticles();
    return (
        <div>
            {articles.map((article: ArticleSimpleResponse) => (
                <Article article={article}/>
            ))}
        </div>
    );
}
