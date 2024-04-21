"use client"

import {BreadcrumbItem, Breadcrumbs} from "@nextui-org/react";

export default function ClientArticleCategoryInfo({ category }: { category: string }){
    return(
        <Breadcrumbs key="bordered" variant="bordered">
            <BreadcrumbItem>카테고리</BreadcrumbItem>
            <BreadcrumbItem>{category}</BreadcrumbItem>
        </Breadcrumbs>
    );
}