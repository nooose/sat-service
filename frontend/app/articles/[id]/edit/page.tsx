import ArticleResponse from "@/model/dto/response/ArticleResponse";
import ArticleUpdate from "@/components/article/article-update";
import {cookies} from "next/headers";
import {get} from "@/utils/client";

export async function generateMetadata({params:{id}} : any) {
    const article = await getArticle(id)
    return {
        title: article.title,
    };
}

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get("/board/categories", cookie);
    return await response.json();
}

export async function getArticle(id: number): Promise<ArticleResponse> {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get(`/board/articles/${id}`, cookie);
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
