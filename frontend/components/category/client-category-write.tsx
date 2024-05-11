"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import React, {useState} from "react";
import {useRouter} from "next/navigation";
import {RestClient} from "@/utils/rest-client";
import {errorToast} from "@/utils/toast-utils";

export default function ClientCategoryWrite() {
    const [name, setName] = useState('');
    const [parentId, setParentId] = useState(null);
    const [isNameError, setIsNameError] = useState(false);
    const [nameErrorMessage, setNameErrorMessage] = useState("");

    const router = useRouter();

    const saveCategory = () => {
        const request: CategoryCreateRequest = {
            name: name,
            parentId: parentId,
        }
        RestClient.post("/board/categories")
            .requestBody(request)
            .successHandler(() => {
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
            }).fetch();
    }

    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   isInvalid={isNameError}
                   errorMessage={nameErrorMessage}
                   onChange={event => setName(event.target.value)}
            />
            <Button color="primary" onClick={saveCategory}>등록</Button>
        </div>
    );
}
