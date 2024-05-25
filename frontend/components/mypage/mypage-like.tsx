import {Card, CardBody, Link} from "@nextui-org/react";
import {format} from "date-fns/format";
import LikedArticleResponse from "@/model/dto/response/LikedArticleResponse";

export default function MypageLike({like}: { like: LikedArticleResponse }) {
    const formattedTime = format(like.createdDateTime, `yyyy-MM-dd h:mm a`)

    return (
        <div>
            <Card key={like.id}>
                <CardBody>
                    <div className="flex justify-between">
                        <Link
                            href={`/articles/${like.articleId}`}
                        >
                            {like.articleTitle}
                        </Link>
                        <div className="font-semibold text-default-400 text-xs">
                            {formattedTime}
                        </div>
                    </div>
                </CardBody>
            </Card>
        </div>
    );
}
