import React, { Suspense } from "react";
import Categories from "@/components/category/categories";
import CategoryCreateButton from "@/components/category/client-category-write-button";


export default function category() {
    return (
        <div>
            <CategoryCreateButton/>
            <Categories/>
        </div>
    );
}
