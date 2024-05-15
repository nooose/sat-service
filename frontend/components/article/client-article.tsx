"use client"

import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import {Card, CardBody, CardFooter} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import styles from "@styles/article.module.css"
import {PressEvent} from "@react-types/shared/src/events";

export default function ClientArticle({article}: { article: ArticleSimpleResponse }) {
    const router = useRouter();
    const onClick = (e: PressEvent) => {
        console.log("하이");
        router.push(`/articles/${article.id}`);
    }

    return (
        <div className={styles.article}>
            <Card key={article.id}
                  fullWidth={true}
                  isPressable
                  disableAnimation={true}
                  onPress={onClick}
            >
                <CardBody>
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