"use client"

import {useRouter} from "next/navigation";
import React from "react";
import {Button} from "@nextui-org/react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";

export default function CategoryUpdateButton({category}: {category: CategoryResponse}) {
    const router = useRouter();

    const onClick = () => {
        router.push(`/category/${category.id}/edit`);
    }
    return (
        <Button color={"success"} size={"sm"} onClick={onClick}>수정</Button>
    );
}