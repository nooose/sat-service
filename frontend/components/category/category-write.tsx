"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import {post} from "@/utils/client";
import {useState} from "react";
import {useRouter} from "next/navigation";

function saveCategory(categoryCreateRequest: CategoryCreateRequest) {
    return post("/board/categories", categoryCreateRequest);
}

function createButtonClick(name: string, parentId: number|null|undefined, router: any) {
    const request: CategoryCreateRequest = {
        name: name,
        parentId: parentId,
    }
    saveCategory(request)
        .then(response => {
            if (response.ok) {
                router.push('/category');
            }
        })
        .catch(error => {
            console.error('API 요청 중 오류가 발생하였습니다:', error);
        });
}

export default function CategoryWrite() {
    const [name, setName] = useState('');
    const [parentId, setParentId] = useState(null);
    const router = useRouter();
    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   onChange={event => setName(event.target.value)}
            />
            <Button color="primary" onClick={() => {createButtonClick(name, parentId, router)}}>등록</Button>
        </div>
    );
}
