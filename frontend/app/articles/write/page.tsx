import ArticleWrite from "@/components/article/article-write";

async function getCategories() {
    const response = await fetch("/board/categories");
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
