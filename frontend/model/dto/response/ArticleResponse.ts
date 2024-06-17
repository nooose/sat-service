interface ArticleResponse {
    id: number;
    title: string;
    content: string;
    category: string;
    hasLike: boolean;
    createdBy: number;
    createdName: string;
}

export default ArticleResponse;
