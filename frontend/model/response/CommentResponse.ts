interface CategoryResponse {
    memberId: number,
    memberName?: string,
    id: number,
    content: string,
    children: CategoryResponse[],
    parentId?: number,
    isDeleted: boolean,
}

export default CategoryResponse;
