import ArticleInfo, {getArticle} from "@/components/article/article-info";

export async function generateMetadata({params:{id}} : any) {
    const article = await getArticle(id)
    return {
        title: article.title,
    };
}

export const revalidate = 0;

export default async function ArticlePage({params:{id}} : any) {
    return (
        <ArticleInfo id={id}/>
    );
}
