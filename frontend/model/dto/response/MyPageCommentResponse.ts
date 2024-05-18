interface MyPageCommentResponse {
    id: number,
    content: string,
    parentId?: number,
    isDeleted: boolean,
    articleId: number,
    articleTitle: string,
    createdDateTime: string,
}

export default MyPageCommentResponse;
