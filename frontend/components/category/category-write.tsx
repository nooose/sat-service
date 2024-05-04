"use client"

import {Input} from "@nextui-org/input";
import {Button, useDisclosure} from "@nextui-org/react";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import React, {useState} from "react";
import {useRouter} from "next/navigation";
import {RestClient} from "@/utils/restClient";
import ErrorModal from "@/components/modal/error-modal";

export default function CategoryWrite() {
    const [name, setName] = useState('');
    const [parentId, setParentId] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const disclosure = useDisclosure();
    const router = useRouter();

    const saveCategory = () => {
        console.log("머야");
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
            .errorHandler(message => {
                setErrorMessage(message);
                disclosure.onOpen();
            }).fetch();
    }

    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   onChange={event => setName(event.target.value)}
            />
            <Button color="primary" onClick={saveCategory}>등록</Button>
            <ErrorModal message={errorMessage} disclosure={disclosure}></ErrorModal>
        </div>
    );
}
