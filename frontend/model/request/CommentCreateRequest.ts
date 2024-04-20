interface CommentCreateRequest {
    content: string,
    parentId: number | null | undefined,
}

export default CommentCreateRequest;
