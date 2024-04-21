import React from "react";
import CategoryUpdate from "@/components/category/category-update";


export default async function CategoryEditPage({params: {id}}: any) {
    return (
        <CategoryUpdate id={id}/>
    );
}