import CommentResponse from "@/model/dto/response/CommentResponse";
import Comment from "@/components/comment/comment";
import {get} from "@/utils/client";
import {cookies} from "next/headers";
import {getUserInfo} from "@/components/user-login";

async function getComments(articleId: number) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get(`/board/articles/${articleId}/comments`, cookie);
    return await response.json();
}

export default async function Comments({articleId}: {articleId: number}) {
    const cookie = cookies().get("JSESSIONID")?.value
    const user = await getUserInfo(cookie);
    const comments = await getComments(articleId);

    return (
        <div>
            {comments?.map((comment: CommentResponse) => (
                <Comment articleId={articleId} comment={comment} loginUserId={user.id}/>
            ))}
        </div>
    )
};

