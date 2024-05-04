import {Input, Textarea} from "@nextui-org/input";
import ArticleResponse from "@/model/dto/response/ArticleResponse";
import ClientArticleUpdateButton from "@/components/article/client-article-update-button";
import {cookies} from "next/headers";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";
import {RestClient} from "@/utils/restClient";

export async function getArticle(id: number): Promise<ArticleResponse> {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/board/articles/${id}`)
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function ArticleInfo({id}: any) {
    const article = await getArticle(id);

    return (
        <div>
            <ClientArticleCategoryInfo category={article.category}/>
            <Input
                isReadOnly
                type="text"
                label="제목"
                labelPlacement={"outside"}
                variant="bordered"
                defaultValue={article.title}
                className="max-w-xs"
            />
            <Textarea
                isReadOnly
                label="내용"
                variant="bordered"
                labelPlacement="outside"
                placeholder="Enter your description"
                defaultValue={article.content}
                className="max-w-xs"
            />
            <ClientArticleUpdateButton id={id}/>
        </div>
    );
}