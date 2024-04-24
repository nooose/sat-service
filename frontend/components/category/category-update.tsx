"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import CategoryUpdateRequest from "@/model/dto/request/CategoryUpdateRequest";
import {put} from "@/utils/client";
import {state} from "sucrase/dist/types/parser/traverser/base";
import {useState} from "react";
import {id} from "postcss-selector-parser";
import CategoryResponse from "@/model/dto/response/CategoryResponse";


function updateCategory(id: number, categoryUpdateRequest: CategoryUpdateRequest) {
    return put(`/board/category/${id}`, categoryUpdateRequest);
}

export default function CategoryUpdate({category}: { category: CategoryResponse }) {
    const router = useRouter();
    const [name, setName] = useState(category.name)
    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   value={name}
                   onChange={event => setName(event.target.value)}
            />
            <Button color="primary" onClick={
                () => {
                    const request: CategoryUpdateRequest = {
                        name: name,
                        parentId: category.parentId,
                    }
                    updateCategory(category.id, request)
                        .then(response => {
                            if (response.ok) {
                                router.push('/category');
                            }
                        })
                        .catch(error => {
                            console.error('API 요청 중 오류가 발생하였습니다:', error);
                        });
                }
            }>수정</Button>
        </div>
    );
}
