"use client"

import {Input, Textarea} from "@nextui-org/input";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {useRouter} from "next/navigation";
import {post} from "@/utils/client";
import ArticleCreateRequest from "@/model/dto/request/ArticleCreateRequest";
import React, {useState} from "react";
import {Button} from "@nextui-org/react";
import {Autocomplete, AutocompleteItem} from "@nextui-org/autocomplete";

function saveArticle(articleCreateRequest: ArticleCreateRequest) {
    return post("/board/articles", articleCreateRequest);
}

export default function ClientArticleWrite({categories}: { categories: CategoryResponse[] }) {
    const router = useRouter();

    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");
    const [categoryId, setCategoryId] = useState(0);

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
                   onChange={event => setTitle(event.target.value)}
            />
            <Textarea
                label="내용"
                placeholder="내용을 입력해 주세요"
                className="max-w-xs"
                value={content}
                onChange={event => setContent(event.target.value)}
            />
            <Button color="primary" onClick={() => {
                const request: ArticleCreateRequest = {
                    title: title,
                    content: content,
                    categoryId: categoryId,
                }
                saveArticle(request)
                    .then(response => {
                        if (response.ok) {
                            router.push('/');
                            router.refresh();
                        }
                    })
                    .catch(error => {
                        console.error('API 요청 중 오류가 발생하였습니다:', error);
                    });
            }}>등록</Button>
        </div>
    );
}
