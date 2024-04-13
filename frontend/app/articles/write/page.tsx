import ArticleWrite from "@/components/article/article-write";

async function getCategories() {
    const config = {
        headers: {
            'Accept': 'application/json',
        }
    }
    const response = await fetch("http://localhost:8080/board/categories", config);
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
