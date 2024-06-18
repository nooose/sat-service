import {Input} from "@nextui-org/input";
import ArticleResponse from "@/model/dto/response/ArticleResponse";
import ClientArticleUpdateButton from "@/components/article/client-article-update-button";
import {cookies} from "next/headers";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";
import {RestClient} from "@/utils/rest-client";
import ArticleLikeButton from "@/components/article/client-article-like-button";
import React from "react";
import dynamic from "next/dynamic";
import {BreadcrumbItem, Breadcrumbs, Card, CardBody, Image} from "@nextui-org/react";
import {Skeleton} from "@nextui-org/skeleton";
import {getUserInfo} from "@/components/user-login";
import styles from "@styles/article.article-view.module.css"
import ClientArticleDeleteButton from "@/components/article/client-article-delete-button";
import category from "@/app/category/page";
import ClientMember from "@/components/member/client-member";


export async function getArticle(id: number): Promise<ArticleResponse> {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/board/articles/${id}`)
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function ArticleInfo({id}: any) {
    const cookie = cookies().get("JSESSIONID")?.value
    const userInfo = await getUserInfo(cookie);
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
        <div className={styles.articleViewContainer}>
            <ClientArticleCategoryInfo category={article.category}/>
            <ClientMember memberId={article.createdBy} memberName={article.createdByName}/>

            <div className="flex gap-4 items-center">
                <Input
                    isReadOnly
                    type="text"
                    label="제목"
                    variant="bordered"
                    defaultValue={article.title}
                    className="max-w-xs"
                />
                {
                    userInfo.isSameId(article.createdBy) &&
                    <ClientArticleUpdateButton id={id}/>
                }
                {
                    userInfo.isSameId(article.createdBy) &&
                    <ClientArticleDeleteButton id={id}/>
                }
            </div>
            <Card>
                <CardBody>
                    <DynamicClientEditorViewer initialValue={article.content}/>
                </CardBody>
            </Card>
            <ArticleLikeButton id={id} hasLike={article.hasLike}/>
        </div>
    );
}
