import CommentResponse from "@/model/response/CommentResponse";
import Comment from "@/components/comment/comment";
import {get} from "@/utils/client";


async function getComments(articleId: number) {
    const response = await get(`/board/articles/${articleId}/comments`);
    return await response.json();
}

export default async function Comments({articleId}: {articleId: number}) {
    const comments = await getComments(articleId);

    return (
        <div>
            {comments?.map((comment: CommentResponse) => (
                <Comment comment={comment}/>
            ))}
        </div>
    )
};

