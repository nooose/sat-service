interface CommentResponse {
    memberId: number,
    memberName?: string,
    id: number,
    content: string,
    children: CommentResponse[],
    parentId?: number,
    isDeleted: boolean,
}

export default CommentResponse;
