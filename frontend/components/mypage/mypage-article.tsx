import ArticleSimpleResponse from "@/model/dto/response/ArticleSimpleResponse";
import {Card, CardBody, CardFooter, Link} from "@nextui-org/react";

export default function MypageArticle({article}: { article: ArticleSimpleResponse }) {
    return (
        <div>
            <Card key={article.id} style={{width: "200%"}}>
                <CardBody>
                    <Link
                        href={`/articles/${article.id}`}
                    >
                        {article.title}
                    </Link>
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
