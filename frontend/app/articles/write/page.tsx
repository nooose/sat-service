import ArticleWrite from "@/components/article/article-write";
import {httpClient} from "@/utils/client";

async function getCategories() {
    const response = await httpClient("/board/categories");
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
