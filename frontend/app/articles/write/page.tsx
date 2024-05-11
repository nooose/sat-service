import {cookies} from "next/headers";
import {RestClient} from "@/utils/restClient";
import dynamic from "next/dynamic";
import {Card} from "@nextui-org/react";
import {Skeleton} from "@nextui-org/skeleton";

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/board/categories?flatten=true")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function ArticleWritePage() {
    const categories = await getCategories();

    const ClientDynamicArticleWrite = dynamic(() => import("@/components/article/client-article-write"), {
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
        <ClientDynamicArticleWrite categories={categories}/>
    );
}
