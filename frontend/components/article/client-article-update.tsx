"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import ArticleUpdateRequest from "@/model/dto/request/ArticleUpdateRequest";
import articleResponse from "@/model/dto/response/ArticleResponse";
import React, {useRef, useState} from "react";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";
import {RestClient} from "@/utils/rest-client";
import ClientEditor from "@/components/editor/client-editor";
import {errorToast} from "@/utils/toast-utils";
import styles from "@styles/article.article-view.module.css";

export default function ClientArticleUpdate({article}: { article: articleResponse }) {
    const [title, setTitle] = useState(article.title);
    const [isTitleError, setIsTitleError] = useState(false);
    const [titleErrorMessage, setTitleErrorMessage] = useState("");

    const editorRef = useRef<any>(null);
    const router = useRouter();

    const updateArticle = () => {
        const content = editorRef.current.getInstance().getMarkdown()
        const request: ArticleUpdateRequest = {
            title: title,
            content: content,
        }

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
                    errorToast(contentError);
                }
            })
            .fetch();
    };

    return (
        <div className={styles.articleViewContainer}>
            <ClientArticleCategoryInfo category={article.category}/>
            <Input type="text" label="제목" placeholder="제목을 입력해 주세요"
                   value={title}
                   defaultValue={title}
                   isInvalid={isTitleError}
                   errorMessage={titleErrorMessage}
                   onValueChange={value => setTitle(value)}
            />
            <ClientEditor initialValue={article.content} editorRef={editorRef}/>
            <Button color="primary" onClick={updateArticle}>수정</Button>
        </div>
    );
}
