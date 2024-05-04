import ClientArticleWrite from "@/components/article/client-article-write";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/restClient";

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/board/categories?flatten=true")
        .session(cookie)
        .fetch();
    return await response.json();
}

export default async function ArticleWritePage() {
    const categories = await getCategories();
    return (
        <ClientArticleWrite categories={categories}/>
    );
}
