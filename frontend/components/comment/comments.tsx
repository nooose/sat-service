import CommentResponse from "@/model/dto/response/CommentResponse";
import Comment from "@/components/comment/comment";
import {cookies} from "next/headers";
import {getUserInfo} from "@/components/user-login";
import {RestClient} from "@/utils/restClient";

async function getComments(articleId: number) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/board/articles/${articleId}/comments`)
        .session(cookie)
        .fetch();
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

