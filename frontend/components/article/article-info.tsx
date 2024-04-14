export async function getArticle(id: number) {
    const response = await fetch(`/board/articles/${id}`);
    return await response.json();
}

export default async function ArticleInfo({id}: any){
    const article = await getArticle(id);
    return (
        <div>
            <h1>{article.title}</h1>
            <h2>{article.category}</h2>
            <p>{article.content}</p>
        </div>
    );
}