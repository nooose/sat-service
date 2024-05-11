import {Input} from "@nextui-org/input";
import ArticleResponse from "@/model/dto/response/ArticleResponse";
import ClientArticleUpdateButton from "@/components/article/client-article-update-button";
import {cookies} from "next/headers";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";
import {RestClient} from "@/utils/restClient";
import ArticleLikeButton from "@/components/article/client-article-like-button";
import React from "react";
import dynamic from "next/dynamic";
import {Card, CardBody} from "@nextui-org/react";
import {Skeleton} from "@nextui-org/skeleton";

export async function getArticle(id: number): Promise<ArticleResponse> {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/board/articles/${id}`)
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function ArticleInfo({id}: any) {
    const article = await getArticle(id);

    const DynamicClientEditorViewer = dynamic(() => import("@/components/editor/client-editor-viewer"), {
        ssr: false,
        loading: () => <div>
            <Card className="h-[calc(100vh - 380px)]" radius="lg">
                <Skeleton className="rounded-lg h-full">
                    <div className="h-24 rounded-lg bg-default-300"></div>
                </Skeleton>
            </Card>
        </div>,
    });

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
            <Card>
                <CardBody>
                    <DynamicClientEditorViewer initialValue={article.content}/>
                </CardBody>
            </Card>
            <div className="flex gap-4 items-center">
                <ClientArticleUpdateButton id={id}/>
                <ArticleLikeButton id={id} hasLike={article.hasLike}/>
            </div>
        </div>
    );
}
