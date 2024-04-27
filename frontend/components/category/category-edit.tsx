"use client"

import CategoryResponse from "@/model/dto/response/CategoryResponse";
import React, {useState} from "react";
import {Input} from "@nextui-org/input";
import {useRouter} from "next/navigation";
import CategoryUpdateRequest from "@/model/dto/request/CategoryUpdateRequest";
import {Button} from "@nextui-org/react";
import {put} from "@/utils/client";
import styles from "@styles/category.module.css";

function updateCategory(id: number, categoryUpdateRequest: CategoryUpdateRequest) {
    return put(`/board/category/${id}`, categoryUpdateRequest);
}

function editButtonClick(name: string, category: CategoryResponse, setIsEdit: (isEdit: boolean) => void, router: any) {
    const request: CategoryUpdateRequest = {
        name: name,
        parentId: category.parentId,
    }

    updateCategory(category.id, request)
        .then(response => {
            if (response.ok) {
                setIsEdit(false);
                router.push('/category');
            }
        })
        .catch(error => {
            console.error('API 요청 중 오류가 발생하였습니다:', error);
        });
}

export default function CategoryEdit({category, setIsEdit}: {category: CategoryResponse, setIsEdit: (isEdit: boolean) => void}) {
    const [name, setName] = useState(category.name);
    const router = useRouter()

    return (
        <div className={styles.editContainer}>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                          value={name}
                          onChange={event => setName(event.target.value)}
            />
            <div className={styles.editContainer}>
                <Button className={styles.editButtonContainer} color="primary" size={"md"} onClick={() => editButtonClick(name, category, setIsEdit, router)}>수정</Button>
                <Button className={styles.editButtonContainer} color="danger" size={"md"} onClick={() => setIsEdit(false)}>
                    취소
                </Button>
            </div>
        </div>
    )
};