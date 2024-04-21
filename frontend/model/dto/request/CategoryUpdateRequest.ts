interface CategoryUpdateRequest {
    id?: number,
    name: string,
    parentId: number | null | undefined,
}

export default CategoryUpdateRequest;
