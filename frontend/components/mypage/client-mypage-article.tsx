"use client"

import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import {Card, CardBody, CardFooter} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import styles from "@styles/mypage.module.css"

export default function ClientMyPageArticle({article}: { article: ArticleSimpleResponse }) {
    const router = useRouter();
    const onClick = () => {
        router.push(`/articles/${article.id}`);
    }

    return (
        <div>
            <Card key={article.id} className={styles.articleHistory}>
                <CardBody onClick={onClick}>
                    <p>{article.title}</p>
                </CardBody>
                <CardFooter className="gap-3">
                    <div className="flex gap-1">
                        <p className="font-semibold text-default-400 text-small">{article.commentCount}</p>
                        <p className=" text-default-400 text-small">댓글</p>
                    </div>
                    <div className="flex gap-1">
                        <p className="font-semibold text-default-400 text-small">{article.likeCount}</p>
                        <p className="text-default-400 text-small">좋아요</p>
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
}
