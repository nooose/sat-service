"use client"

import {Input, Textarea} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";
import ArticleUpdateRequest from "@/model/dto/request/ArticleUpdateRequest";
import articleResponse from "@/model/dto/response/ArticleResponse";
import {useState} from "react";
import ClientArticleCategoryInfo from "@/components/article/client-article-category-info";
import {RestClient} from "@/utils/restClient";
import {id} from "postcss-selector-parser";

export default function ClientArticleUpdate({article}: { article: articleResponse }) {
    const [title, setTitle] = useState(article.title);
    const [content, setContent] = useState(article.content);
    const router = useRouter();

    const updateArticle = () => {
        const request: ArticleUpdateRequest = {
            title: title,
            content: content,
        }

        RestClient.put(`/board/articles/${id}`)
            .requestBody(request)
            .successHandler(() => {
                router.push(`/articles/${article.id}`);
                router.refresh();
            }).fetch();
    };

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
            <Button color="primary" onClick={updateArticle}>수정</Button>
        </div>
    );
}
