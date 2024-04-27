"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import {post} from "@/utils/client";
import React, {useState} from "react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@/styles/category.module.css"

function saveCategory(categoryCreateRequest: CategoryCreateRequest) {
    return post("/board/categories", categoryCreateRequest);
}

function createButtonClick(name: string, parentCategory: CategoryResponse, setIsCreateOpen: (isCreateOpen: boolean) => void, router: any) {
    const request: CategoryCreateRequest = {
        name: name,
        parentId: parentCategory.id,
    }
    saveCategory(request)
        .then(response => {
            if (response.ok) {
                setIsCreateOpen(false)
                router.push('/category');
                router.refresh();
            }
        })
        .catch(error => {
            console.error('API 요청 중 오류가 발생하였습니다:', error);
        });
}

export default function ChildCategoryWrite({ parentCategory, setIsCreateOpen }: { parentCategory: CategoryResponse, setIsCreateOpen: (isCreateOpen: boolean) => void }) {
    const [name, setName] = useState('');
    const router = useRouter();

    return (
        <div>
            <div>상위 카테고리 : {parentCategory.name}</div>
            <div className={styles.childCreateContainer}>
                <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                       onChange={event => setName(event.target.value)}
                />
                <div>
                    <Button className={styles.childCreateButtonContainer} color="primary" size={"md"} onClick={() => createButtonClick(name, parentCategory, setIsCreateOpen, router)}>등록</Button>
                    <Button className={styles.childCreateButtonContainer} color="danger" size={"md"} onClick={() => setIsCreateOpen(false)}>취소</Button>
                </div>
            </div>
        </div>
    );
}
