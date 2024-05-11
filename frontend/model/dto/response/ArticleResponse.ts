interface ArticleResponse {
    id: number;
    title: string;
    content: string;
    category: string;
    hasLike: boolean;
    createdBy: number;
}

export default ArticleResponse;
