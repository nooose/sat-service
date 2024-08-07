interface ArticleSimpleResponse {
    id: number;
    title: string;
    category: string;
    commentCount: number;
    likeCount: number;
    views: number;
    createdDateTime: string;
}

export default ArticleSimpleResponse;
