import ArticleResponse from "@/model/dto/response/ArticleResponse";
import {cookies} from "next/headers";
import ClientArticleUpdate from "@/components/article/client-article-update";
import {RestClient} from "@/utils/restClient";

export async function generateMetadata({params:{id}} : any) {
    const article = await getArticle(id)
    return {
        title: article.title,
    };
}

export async function getArticle(id: number): Promise<ArticleResponse> {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/board/articles/${id}`)
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function ArticleEditPage({params:{id}} : any) {
    const article = await getArticle(id)
    return (
        <div>
            <ClientArticleUpdate
                article={article}
            />
        </div>
    );
}
