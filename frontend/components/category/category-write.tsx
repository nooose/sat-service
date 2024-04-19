"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import {httpClient} from "@/utils/client";
import CategoryCreateRequest from "@/model/request/CategoryCreateRequest";
import categoryCreateStore from "@/store/category-create-store";

function saveCategory(categoryCreateRequest: CategoryCreateRequest) {
    return httpClient("/board/categories", "POST", categoryCreateRequest);
}

export default function CategoryWrite() {
    const state = categoryCreateStore((state: any) => state);
    const router = useRouter();

    return (
        <div>
            {state.parentName !== undefined? <div>상위 카테고리 : {state.parentName}</div> : ''}
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   onChange={event => state.setName(event.target.value)}
            />
            <Button color="primary" onClick={
                () => {
                    const request: CategoryCreateRequest = {
                        name: state.name,
                        parentId: state.parentId,
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
            }>등록</Button>
        </div>
    );
}
