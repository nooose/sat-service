import React, {Suspense} from "react";
import Categories from "@/components/category/categories";
import CategoryCreateButton from "@/components/category/category-write-button";


export default function category() {
    return (
        <div>
            <div>
                <CategoryCreateButton buttonName={"루트카테고리"}/>
            </div>
            <Suspense>
                <Categories/>
            </Suspense>
        </div>
    );
}
