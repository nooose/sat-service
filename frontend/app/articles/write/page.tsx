import ArticleWrite from "@/components/article/article-write";
import {httpClient} from "@/utils/client";
import {cookies} from "next/headers";

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await httpClient("/board/categories", "GET", null, cookie);
    return await response.json();
}

export default async function ArticleWritePage() {
    const categories = await getCategories();
    return (
        <div>
            <ArticleWrite categories={categories}/>
        </div>
    );
}
