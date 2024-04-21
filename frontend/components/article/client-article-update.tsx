"use client"

import {Input, Textarea} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import {put} from "@/utils/client";
import ArticleUpdateRequest from "@/model/dto/request/ArticleUpdateRequest";
import articleResponse from "@/model/dto/response/ArticleResponse";
import {useState} from "react";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";

function updateArticle(id: number, request: ArticleUpdateRequest) {
    return put(`/board/articles/${id}`, request);
}

export default function ClientArticleUpdate({article}: { article: articleResponse }) {
    const [title, setTitle] = useState(article.title);
    const [content, setContent] = useState(article.content);

    const router = useRouter();
    return (
        <div>
            <ClientArticleCategoryInfo category={article.category}/>
            <Input type="text" label="제목" placeholder="제목을 입력해 주세요"
                   value={title}
                   defaultValue={title}
                   onValueChange={value => setTitle(value)}
            />
            <Textarea
                label="내용"
                placeholder="내용을 입력해 주세요"
                className="max-w-xs"
                value={content}
                defaultValue={article.content}
                onValueChange={value => setContent(value)}
            />
            <Button color="primary" onClick={() => {
                    const request: ArticleUpdateRequest = {
                        title: title,
                        content: content,
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
