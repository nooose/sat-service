import ArticleSimpleResponse from "@/model/response/ArticleSimpleResponse";
import {Card, CardBody} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export async function getArticle(id: number) {
    const config = {
        headers: {
            'Accept': 'application/json',
        }
    }
    const response = await fetch(`http://localhost:8080/board/articles/${id}`, config);
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