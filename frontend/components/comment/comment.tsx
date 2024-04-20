import styles from "@styles/category.module.css";
import {Card, CardBody, Divider, Link, Image} from "@nextui-org/react";
import React from "react";
import CommentResponse from "@/model/response/CommentResponse";
import {CardFooter, CardHeader} from "@nextui-org/card";

export default function Comment({ comment }: { comment: CommentResponse }) {
    return (
        <div style={{marginTop: 30}}>
            <Card className="max-w-[600px]" style={{width: 600}} key={comment.id}>
                <CardHeader className="flex gap-3">
                    <Image
                        alt="nextui logo"
                        height={40}
                        radius="sm"
                        src="https://avatars.githubusercontent.com/u/86160567?s=200&v=4"
                        width={40}
                    />
                    <div className="flex flex-col">
                        <p>{comment.memberName}</p>
                    </div>
                </CardHeader>
                <Divider/>
                <CardBody>
                    <p className="flex gap-10 items-center">
                        {comment.content}
                    </p>
                </CardBody>
                <Divider/>
            </Card>
            {comment.children?.map((comment: CommentResponse) => (
                <Comment comment={comment} key={comment.id}/>
            ))}
        </div>
    );

};