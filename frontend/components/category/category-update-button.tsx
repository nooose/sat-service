"use client"

import {useRouter} from "next/navigation";
import React from "react";
import {Button} from "@nextui-org/react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import categoryUpdateStore from "@/store/category-update-store";

export default function CategoryUpdateButton({category}: {category: CategoryResponse}) {
    const router = useRouter();
    // TODO: useSate 사용 Store 제거
    const state = categoryUpdateStore((state: any) => state);

    const onClick = () => {
        state.setName(category.name)
        state.setParentId(category.parentId);
        router.push(`/category/${category.id}/edit`);
    }
    return (
        <Button color={"success"} size={"sm"} onClick={onClick}>수정</Button>
    );
}