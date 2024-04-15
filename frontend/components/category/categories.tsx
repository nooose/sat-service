import {httpClient} from "@/utils/client";
import React from "react";
import Category from "@/components/category/category";
import CategoryResponse from "@/model/response/CategoryResponse";

async function getCategories() {
    const response = await httpClient("/board/categories");
    return await response.json();
}

export default async function Categories() {
    const categories = await getCategories();
    return (
        <div>
            {categories.map((category: CategoryResponse) => (
                <Category category={category}/>
            ))}
        </div>
    )
};