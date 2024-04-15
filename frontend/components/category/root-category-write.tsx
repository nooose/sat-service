"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import {httpClient} from "@/utils/client";
import RootCategoryCreateRequest from "@/model/request/RootCategoryCreateRequest";
import categoryStore from "@/store/category-store";
import category from "@/app/category/page";

function saveCategory(rootCategoryCreateRequest: RootCategoryCreateRequest) {
    return httpClient("/board/categories", "POST", rootCategoryCreateRequest);
}

export default function RootCategoryWrite() {
    const state = categoryStore((state: any) => state);
    const router = useRouter();

    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   value={state.name}
                   onChange={event => state.setName(event.target.value)}
            />
            <Button color="primary" onClick={
                () => {
                    const request: RootCategoryCreateRequest = {
                        name: state.name
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
