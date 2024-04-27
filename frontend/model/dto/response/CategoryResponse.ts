interface CategoryResponse {
    id: number;
    name: string;
    parentId: number
    children: CategoryResponse[];
}

export default CategoryResponse;
