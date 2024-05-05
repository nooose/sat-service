"use client"

import {Input, Textarea} from "@nextui-org/input";
import {Button, useDisclosure} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import ArticleUpdateRequest from "@/model/dto/request/ArticleUpdateRequest";
import articleResponse from "@/model/dto/response/ArticleResponse";
import React, {useState} from "react";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";
import {RestClient} from "@/utils/restClient";
import ErrorModal from "@/components/modal/error-modal";

export default function ClientArticleUpdate({article}: { article: articleResponse }) {
    const [title, setTitle] = useState(article.title);
    const [content, setContent] = useState(article.content);
    const [isTitleError, setIsTitleError] = useState(false);
    const [titleErrorMessage, setTitleErrorMessage] = useState("");
    const [isContentError, setIsContentError] = useState(false);
    const [contentErrorMessage, setContentErrorMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const disclosure = useDisclosure();
    const router = useRouter();

    const updateArticle = () => {
        const request: ArticleUpdateRequest = {
            title: title,
            content: content,
        }

        console.log(`/board/articles/${article.id}`);
        RestClient.put(`/board/articles/${article.id}`)
            .requestBody(request)
            .successHandler(() => {
                router.push(`/articles/${article.id}`);
                router.refresh();
            })
            .errorHandler(error => {
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
    };

    return (
        <div>
            <ClientArticleCategoryInfo category={article.category}/>
            <Input type="text" label="제목" placeholder="제목을 입력해 주세요"
                   value={title}
                   defaultValue={title}
                   isInvalid={isTitleError}
                   errorMessage={titleErrorMessage}
                   onValueChange={value => setTitle(value)}
            />
            <Textarea
                label="내용"
                placeholder="내용을 입력해 주세요"
                className="max-w-xs"
                value={content}
                defaultValue={article.content}
                isInvalid={isContentError}
                errorMessage={contentErrorMessage}
                onValueChange={value => setContent(value)}
            />
            <Button color="primary" onClick={updateArticle}>수정</Button>
            <ErrorModal message={errorMessage} disclosure={disclosure}/>
        </div>
    );
}
