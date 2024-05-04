"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import React, {useState} from "react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@/styles/category.module.css"
import {RestClient} from "@/utils/restClient";

export default function ChildCategoryWrite({parentCategory, setIsCreateOpen}: {
    parentCategory: CategoryResponse,
    setIsCreateOpen: (isCreateOpen: boolean) => void
}) {
    const [name, setName] = useState('');
    const router = useRouter();

    const createButtonClick = () => {
        const request: CategoryCreateRequest = {
            name: name,
            parentId: parentCategory.id,
        }
        RestClient.post("/board/categories")
            .requestBody(request)
            .successHandler(() => {
                setIsCreateOpen(false)
                router.push('/category');
                router.refresh();
            }).fetch();
    }

    return (
        <div>
            <div>상위 카테고리 : {parentCategory.name}</div>
            <div className={styles.childCreateContainer}>
                <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                       onChange={event => setName(event.target.value)}
                />
                <div>
                    <Button className={styles.childCreateButtonContainer} color="primary" size={"md"}
                            onClick={createButtonClick}>등록</Button>
                    <Button className={styles.childCreateButtonContainer} color="danger" size={"md"}
                            onClick={() => setIsCreateOpen(false)}>취소</Button>
                </div>
            </div>
        </div>
    );
}
