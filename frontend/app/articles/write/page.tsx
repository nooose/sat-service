import ClientArticleWrite from "@/components/article/client-article-write";
import {cookies} from "next/headers";
import {get} from "@/utils/client";

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get("/board/categories?flatten=true", cookie);
    return await response.json();
}

export default async function ArticleWritePage() {
    const categories = await getCategories();
    return (
        <ClientArticleWrite categories={categories}/>
    );
}
