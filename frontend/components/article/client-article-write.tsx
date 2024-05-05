"use client"

import {Input, Textarea} from "@nextui-org/input";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {useRouter} from "next/navigation";
import ArticleCreateRequest from "@/model/dto/request/ArticleCreateRequest";
import React, {useState} from "react";
import {Button, useDisclosure} from "@nextui-org/react";
import {Autocomplete, AutocompleteItem} from "@nextui-org/autocomplete";
import {RestClient} from "@/utils/restClient";
import {CommonErrorResponse} from "@/model/dto/response/CommonErrorResponse";
import ErrorModal from "@/components/modal/error-modal";


export default function ClientArticleWrite({categories}: { categories: CategoryResponse[] }) {
    const router = useRouter();
    const disclosure = useDisclosure();

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [categoryId, setCategoryId] = useState(0);

    const [isTitleError, setIsTitleError] = useState(false);
    const [titleErrorMessage, setTitleErrorMessage] = useState("");
    const [isContentError, setIsContentError] = useState(false);
    const [contentErrorMessage, setContentErrorMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const saveArticle = () => {
        const request: ArticleCreateRequest = {
            title: title,
            content: content,
            categoryId: categoryId,
        }

        return RestClient.post("/board/articles")
            .requestBody(request)
            .successHandler(() => {
                router.push('/');
                router.refresh();
            })
            .errorHandler((error: CommonErrorResponse) => {
                if (error.isBindingError()) {
                    const titleError = error.filedErrorMessage("title");
                    setIsTitleError(!!titleError);
                    setTitleErrorMessage(titleError);
                    const contentError = error.filedErrorMessage("content");
                    setIsContentError(!!contentError);
                    setContentErrorMessage(contentError);
                    return;
                }
                disclosure.onOpen();
                setErrorMessage(error.errorMessage());
            })
            .fetch();
    }

    return (
        <div>
            <Autocomplete
                label="카테고리 선택"
                className="max-w-xs"
                onSelectionChange={(input: React.Key) => setCategoryId(parseInt(input.toString()))}
            >{
                categories.map(category => (
                    <AutocompleteItem key={category.id} value={category.id} style={{color: "black"}}>
                        {category.name}
                    </AutocompleteItem>
                ))
            }
            </Autocomplete>
            <Input type="text"
                   label="제목"
                   placeholder="제목을 입력해 주세요"
                   value={title}
                   isInvalid={isTitleError}
                   errorMessage={titleErrorMessage}
                   onChange={event => setTitle(event.target.value)}
            />
            <Textarea
                label="내용"
                placeholder="내용을 입력해 주세요"
                className="max-w-xs"
                value={content}
                isInvalid={isContentError}
                errorMessage={contentErrorMessage}
                onChange={event => setContent(event.target.value)}
            />
            <Button color="primary" onClick={saveArticle}>등록</Button>
            <ErrorModal message={errorMessage} disclosure={disclosure}/>
        </div>
    );
}
