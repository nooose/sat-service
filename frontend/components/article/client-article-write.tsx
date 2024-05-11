"use client"

import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {useRouter} from "next/navigation";
import ArticleCreateRequest from "@/model/dto/request/ArticleCreateRequest";
import React, {useRef, useState} from "react";
import {Autocomplete, AutocompleteItem} from "@nextui-org/autocomplete";
import {RestClient} from "@/utils/restClient";
import {CommonErrorResponse} from "@/model/dto/response/CommonErrorResponse";
import ClientEditor from "@/components/editor/client-editor";
import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/button";
import {toast} from "react-toastify";


export default function ClientArticleWrite({categories}: { categories: CategoryResponse[] }) {
    const router = useRouter();
    const editorRef = useRef<any>(null);

    const [title, setTitle] = useState("");
    const [isTitleError, setIsTitleError] = useState(false);
    const [titleErrorMessage, setTitleErrorMessage] = useState("");
    const [categoryId, setCategoryId] = useState(0);

    const saveArticle = () => {
        const content = editorRef.current.getInstance().getMarkdown()
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
                    toast(contentError);
                }
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
            <ClientEditor initialValue={' '} editorRef={editorRef}/>
            <Button color="primary" onClick={saveArticle}>등록</Button>
        </div>
    );
}
