"use client"

import CategoryResponse from "@/model/dto/response/CategoryResponse";
import React, {useState} from "react";
import {Input} from "@nextui-org/input";
import {useRouter} from "next/navigation";
import {Button} from "@nextui-org/react";
import CategoryUpdateRequest from "@/model/dto/request/CategoryUpdateRequest";
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";

export default function ClientCategoryEdit({category, setIsEdit}: {
    category: CategoryResponse,
    setIsEdit: (isEdit: boolean) => void
}) {
    const [name, setName] = useState(category.name);
    const [isNameError, setIsNameError] = useState(false);
    const [nameErrorMessage, setNameErrorMessage] = useState("");

    const router = useRouter();

    const updateCategory = () => {
        const request: CategoryUpdateRequest = {
            name: name,
            parentId: category.parentId,
        }

        RestClient.put(`/board/categories/${category.id}`)
            .requestBody(request)
            .successHandler(() => {
                setIsEdit(false);
                router.push('/category');
                router.refresh();
            })
            .errorHandler(error => {
                if (error.isBindingError()) {
                    const nameError = error.filedErrorMessage("name");
                    setIsNameError(!!nameError);
                    setNameErrorMessage(nameError);
                    return;
                }
                errorToast(error.errorMessage());
            })
            .fetch();
    }

    return (
        <div>
            <Input type="text" placeholder="카테고리 이름"
                   value={name}
                   isInvalid={isNameError}
                   errorMessage={nameErrorMessage}
                   onChange={event => setName(event.target.value)}
            />
            <div>
                <Button
                    color="primary"
                    size={"sm"}
                    onClick={updateCategory}>
                    수정
                </Button>
                <Button
                        color="danger"
                        size={"sm"}
                        onClick={() => setIsEdit(false)}>
                    취소
                </Button>
            </div>
        </div>
    )
};
