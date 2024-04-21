"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import CategoryUpdateRequest from "@/model/dto/request/CategoryUpdateRequest";
import categoryUpdateStore from "@/store/category-update-store";
import {put} from "@/utils/client";

function updateCategory(id: number, categoryUpdateRequest: CategoryUpdateRequest) {
    return put(`/board/category/${id}`, categoryUpdateRequest);
}

export default function CategoryUpdate({id}: { id: number }) {
    // TODO: useSate 사용 Store 제거
    const state = categoryUpdateStore((state: any) => state);
    const router = useRouter();
    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   value={state.name}
                   onChange={event => state.setName(event.target.value)}
            />
            <Button color="primary" onClick={
                () => {
                    const request: CategoryUpdateRequest = {
                        name: state.name,
                        parentId: state.parentId,
                    }
                    updateCategory(id, request)
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
