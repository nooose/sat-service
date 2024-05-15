"use client"

import {useRouter} from "next/navigation";
import React from "react";
import {Button} from "@nextui-org/react";

export default function CategoryCreateButton() {
    const router = useRouter();
    const onClick = () => {
        router.push("/category/write");
    }

    return (
        <Button
            color={"primary"}
            size={"sm"}
            onClick={onClick}
        >
            루트 카테고리 등록
        </Button>
    );
}