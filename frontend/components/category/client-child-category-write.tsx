"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import React, {useState} from "react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";

export default function ClientChildCategoryWrite({parentCategory, setIsCreateOpen}: {
    parentCategory: CategoryResponse,
    setIsCreateOpen: (isCreateOpen: boolean) => void
}) {
    const [name, setName] = useState('');
    const [isNameError, setIsNameError] = useState(false);
    const [nameErrorMessage, setNameErrorMessage] = useState("");

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
            })
            .errorHandler(data => {
                if (data.isBindingError()) {
                    const nameError = data.filedErrorMessage("name");
                    setIsNameError(!!nameError);
                    setNameErrorMessage(nameError);
                    return;
                }
                errorToast(data.errorMessage());
            })
            .fetch();
    }

    return (
        <div>
            <Input type="text" label="카테고리 이름"
                   isInvalid={isNameError}
                   errorMessage={nameErrorMessage}
                   onChange={event => setName(event.target.value)}
            />
            <div className="flex gap-1">
                <Button
                        color="primary"
                        size={"sm"}
                        onClick={createButtonClick}>
                    등록
                </Button>
                <Button
                        color="danger"
                        size={"sm"}
                        onClick={() => setIsCreateOpen(false)}>
                    취소
                </Button>
            </div>
        </div>
    );
}
