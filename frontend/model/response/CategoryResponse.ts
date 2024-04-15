interface CategoryResponse {
    id: number;
    name: string;
    children: CategoryResponse[];
}

export default CategoryResponse;
