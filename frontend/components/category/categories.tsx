import {get} from "@/utils/client";
import React from "react";
import Category from "@/components/category/category";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {cookies} from "next/headers";

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await get("/board/categories", cookie);
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