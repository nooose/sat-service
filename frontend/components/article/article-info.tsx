import {Input, Textarea} from "@nextui-org/input";
import ArticleResponse from "@/model/dto/response/ArticleResponse";
import {Code} from "@nextui-org/code";
import ArticleUpdateButton from "@/components/article/article-update-button";
import {cookies} from "next/headers";
import {get} from "@/utils/client";

export async function getArticle(id: number): Promise<ArticleResponse> {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get(`/board/articles/${id}`, cookie);
    return await response.json();
}

export default async function ArticleInfo({id}: any){
    const article = await getArticle(id);

    return (
        <div>
            <Code size="md">{article.category}</Code>
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
            <ArticleUpdateButton id={id}/>
        </div>
    );
}