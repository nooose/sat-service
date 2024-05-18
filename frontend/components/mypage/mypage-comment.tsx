import {Card, CardBody, CardFooter, Link} from "@nextui-org/react";
import MyPageCommentResponse from "@/model/dto/response/MyPageCommentResponse";
import {format} from "date-fns/format";

export default function MypageComment({comment}: { comment: MyPageCommentResponse, }) {
    const formattedTime = format(comment.createdDateTime, `yyyy-MM-dd h:mm a`)
    return (
        <div>
            <Card key={comment.id} style={{width: "200%"}}>
                <CardBody>
                    <div className="flex justify-between">
                        <div>
                            {comment.content}
                        </div>
                        <div className="font-semibold text-default-400 text-small">
                            {formattedTime}
                        </div>
                    </div>
                </CardBody>
                <CardFooter>
                    <Link
                        size={"sm"}
                        color={"primary"}
                        href={`/articles/${comment.articleId}`}
                    >
                        {comment.articleTitle}
                    </Link>
                </CardFooter>
            </Card>
        </div>
    );
};