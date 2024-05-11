import ArticleResponse from "@/model/dto/response/ArticleResponse";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import dynamic from "next/dynamic";
import {Card} from "@nextui-org/react";
import {Skeleton} from "@nextui-org/skeleton";

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
    const ClientDynamicArticleUpdate = dynamic(() => import("@/components/article/client-article-update"), {
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
            <ClientDynamicArticleUpdate
                article={article}
            />
        </div>
    );
}
