"use client"

import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import {Card, CardBody} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import styles from "@styles/article.module.css"

export default function ClientArticle({ article }: { article: ArticleSimpleResponse }){
    const router = useRouter();
    const onClick = () => {
        router.push(`/articles/${article.id}`);
    }

    return(
        <div className={styles.article}>
            <Card key={article.id}>
                <CardBody onClick={onClick}>
                    <p>{article.title}</p>
                </CardBody>
            </Card>
        </div>
    );
}