import React, { Suspense } from "react";
import Categories from "@/components/category/categories";
import CategoryCreateButton from "@/components/category/category-write-button";


export default function category() {
    return (
        <div>
            <div>
                <CategoryCreateButton/>
            </div>
            <Suspense>
                <Categories/>
            </Suspense>
        </div>
    );
}
