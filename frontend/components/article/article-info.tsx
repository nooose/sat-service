import {httpClient} from "@/utils/client";
import {Input, Textarea} from "@nextui-org/input";
import ArticleResponse from "@/model/response/ArticleResponse";
import {Code} from "@nextui-org/code";
import {Button} from "@nextui-org/react";
import ArticleUpdateButton from "@/components/article/article-update-button";

export async function getArticle(id: number): Promise<ArticleResponse> {
    const response = await httpClient(`/board/articles/${id}`);
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