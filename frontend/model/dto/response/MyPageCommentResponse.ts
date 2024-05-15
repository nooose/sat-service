import CommentResponse from "@/model/dto/response/CommentResponse";

interface MyPageCommentResponse {
    id: number,
    content: string,
    parentId?: number,
    isDeleted: boolean,
    articleId: number,
    articleTitle: string,
    createdAt: string,
}

export default MyPageCommentResponse;
