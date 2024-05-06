interface CategoryCreateRequest {
    name: string,
    parentId?: number | null,
    parentName?: string,
}

export default CategoryCreateRequest;
