"use client"

import {Input} from "@nextui-org/input";
import {Button, useDisclosure} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import React, {useState} from "react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import styles from "@/styles/category.module.css"
import {RestClient} from "@/utils/restClient";
import ErrorModal from "@/components/modal/error-modal";

export default function ChildCategoryWrite({parentCategory, setIsCreateOpen}: {
    parentCategory: CategoryResponse,
    setIsCreateOpen: (isCreateOpen: boolean) => void
}) {
    const [name, setName] = useState('');
    const [errorMessage, setErrorMessage] = useState("")
    const [isNameError, setIsNameError] = useState(false);
    const [nameErrorMessage, setNameErrorMessage] = useState("");

    const router = useRouter();
    const disclosure = useDisclosure();

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
                disclosure.onOpen();
                setErrorMessage(data.errorMessage());
            })
            .fetch();
    }

    return (
        <div className={styles.childCreateContainer}>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   isInvalid={isNameError}
                   errorMessage={nameErrorMessage}
                   onChange={event => setName(event.target.value)}
            />
            <div>
                <Button className={styles.childCreateButtonContainer} color="primary" size={"md"}
                        onClick={createButtonClick}>등록</Button>
                <Button className={styles.childCreateButtonContainer} color="danger" size={"md"}
                        onClick={() => setIsCreateOpen(false)}>취소</Button>
            </div>
            <ErrorModal message={errorMessage} disclosure={disclosure}></ErrorModal>
        </div>
    );
}
