import React from "react";
import CategoryUpdate from "@/components/category/category-update";
import {cookies} from "next/headers";
import {get} from "@/utils/client";

async function getCategory({id} : {id: number}) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get(`/board/categories/${id}`, cookie);
    return await response.json();
}

export default async function CategoryEditPage({params: {id}}: any) {
    const category = await getCategory(id);
    return (
        <CategoryUpdate category={category}/>
    );
}