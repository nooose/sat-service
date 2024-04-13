import ArticleSimpleResponse from "@/model/response/ArticleSimpleResponse";
import Article from "@/components/article/article";
async function getArticles() {
    const config = {
        headers: {
            'Accept': 'application/json',
        }
    }
    const response = await fetch("http://localhost:8080/board/articles", config);
    return await response.json();
}

export default async function Articles() {
    const articles = await getArticles();
    return(
        <div>
            {articles.map((article : ArticleSimpleResponse) => (
                <Article
                    key={article.id}
                    id={article.id}
                    title={article.title}
                    category={article.category}
                />
            ))}
        </div>
    );
}
