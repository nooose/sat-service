import {httpClient} from "@/utils/client";
import ArticleResponse from "@/model/response/ArticleResponse";
import ArticleUpdate from "@/components/article/article-update";

export async function generateMetadata({params:{id}} : any) {
    const article = await getArticle(id)
    return {
        title: article.title,
    };
}

async function getCategories() {
    const response = await httpClient("/board/categories");
    return await response.json();
}

export async function getArticle(id: number): Promise<ArticleResponse> {
    const response = await httpClient(`/board/articles/${id}`);
    return await response.json();
}

export default async function ArticleEditPage({params:{id}} : any) {
    const categories = await getCategories();
    const article = await getArticle(id)
    return (
        <div>
            <ArticleUpdate
                article={article}
                categories={categories}
            />
        </div>
    );
}
