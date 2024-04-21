interface CategoryCreateRequest {
    name: string,
    parentId: number | null | undefined,
    parentName?: string | null | undefined,
}

export default CategoryCreateRequest;
