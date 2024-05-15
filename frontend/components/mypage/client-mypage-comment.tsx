"use client"

import {Card, CardBody, CardFooter} from "@nextui-org/react";
import MyPageCommentResponse from "@/model/dto/response/MyPageCommentResponse";
import {useRouter} from "next/navigation";
import styles from "@/styles/mypage.module.css"

export default function MyPageClientComment({comment}: { comment: MyPageCommentResponse, }) {
    const router = useRouter();
    const onClick = () => {
        router.push(`/articles/${comment.articleId}`);
    }

    return (
        <div >
            <Card key={comment.id} className={styles.commentHistory}  onClick={onClick}>
                <CardBody>
                    <div className="flex justify-between">
                        <div>
                            {comment.content}
                        </div>
                        <div className="font-semibold text-default-400 text-small">
                            {comment.createdAt}
                        </div>
                    </div>
                </CardBody>
                <CardFooter>
                    <div className="font-semibold text-default-400 text-small">
                        {comment.articleTitle}
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
};