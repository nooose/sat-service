interface ArticleSimpleResponse {
    id: number;
    title: string;
    category: string;
    commentCount: number;
    likeCount: number;
    createdDateTime: string,
}

export default ArticleSimpleResponse;
