import React from "react";
import ClientCategory from "@/components/category/client-category";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";

async function getCategories() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/board/categories")
        .session(cookie)
        .fetch()
    return await response.json();
}

export default async function Categories() {
    // const router = useRouter();
    const categories = await getCategories();
    return (
        <div>
            {categories?.map((category: CategoryResponse) => (
                <ClientCategory category={category}/>
            ))}
        </div>
    )
};