"use client"

import {useRouter} from "next/navigation";
import React from "react";
import {Button} from "@nextui-org/react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import categoryCreateStore from "@/store/category-create-store";

export default function CategoryCreateButton({buttonName, category}: {buttonName: string, category?: CategoryResponse}) {
    const router = useRouter();
    const state = categoryCreateStore((state: any) => state);
    const onClick = () => {
        state.setParentName(category?.name);
        state.setParentId(category?.id);
        router.push("/category/write");
    }

    return (
        <Button color={"primary"} size={"sm"} onClick={onClick}>{buttonName} 등록</Button>
    );
}