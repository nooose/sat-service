"use client"

import {Input, Textarea} from "@nextui-org/input";
import articleStore from "@/store/article-store";
import {Button} from "@nextui-org/react";
import CategoryResponse from "@/model/dto/response/CategoryResponse";
import {useRouter} from "next/navigation";
import {put} from "@/utils/client";
import ArticleUpdateRequest from "@/model/dto/request/ArticleUpdateRequest";
import articleResponse from "@/model/dto/response/ArticleResponse";
import {useEffect} from "react";
import {Code} from "@nextui-org/code";

function updateArticle(id: number, request: ArticleUpdateRequest) {
    return put(`/board/articles/${id}`, request);
}

export default function ArticleUpdate({article, categories}: { article: articleResponse, categories: CategoryResponse[] }) {
    const state = articleStore((state: any) => state);
    useEffect(() => {
        state.setTitle(article.title);
        state.setContent(article.content);
    }, []);
    const router = useRouter();
    return (
        <div>
            <Code size="md">{article.category}</Code>
            <Input type="text" label="제목" placeholder="제목을 입력해 주세요"
                   value={state.title}
                   defaultValue={article.title}
                   onValueChange={value => state.setTitle(value)}
            />
            <Textarea
                label="내용"
                placeholder="내용을 입력해 주세요"
                className="max-w-xs"
                value={state.content}
                defaultValue={article.content}
                onValueChange={value => state.setContent(value)}
            />
            <Button color="primary" onClick={
                () => {
                    const request: ArticleUpdateRequest = {
                        title: state.title,
                        content: state.content,
                    }
                    updateArticle(article.id, request)
                        .then(response => {
                            if (response.ok) {
                                router.push(`/articles/${article.id}`);
                            }
                        })
                        .catch(error => {
                            console.error('API 요청 중 오류가 발생하였습니다:', error);
                        });
                }
            }>수정</Button>
        </div>
    );
}
