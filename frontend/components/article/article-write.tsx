"use client"

import {Input, Textarea} from "@nextui-org/input";
import articleCreateStore from "@/store/article-store";
import {Button} from "@nextui-org/react";
import {Autocomplete, AutocompleteItem} from "@nextui-org/autocomplete";
import CategoryResponse from "@/model/response/CategoryResponse";

function saveArticle(articleCreateRequest: ArticleCreateRequest) {
    fetch("http://localhost:8080/board/articles", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(articleCreateRequest)
    })
}

export default function ArticleWrite({ categories }: { categories: CategoryResponse[] }) {
    const state = articleCreateStore((state: any) => state);

    return (
        <div className="flex w-full flex-wrap md:flex-nowrap gap-4">
            <div>
                <Autocomplete
                    label="카테고리 선택"
                    className="max-w-xs"
                    onSelectionChange={(input: React.Key) => state.setCategoryId(input)}
                >{
                    categories.map(category => (
                        <AutocompleteItem key={category.id} value={category.id}>
                            {category.name}
                        </AutocompleteItem>
                    ))
                }
                </Autocomplete>
            </div>
            <Input type="text" label="제목" placeholder="제목을 입력해 주세요"
                   value={state.title}
                   onChange={event => state.setTitle(event.target.value)}
            />
            <Textarea
                label="내용"
                placeholder="내용을 입력해 주세요"
                className="max-w-xs"
                value={state.content}
                onChange={event => state.setContent(event.target.value)}
            />
            <Button color="primary" onClick={
                () => {
                    const request: ArticleCreateRequest = {
                        title: state.title,
                        content: state.content,
                        categoryId: state.categoryId,
                    }
                    saveArticle(request)
                }
            }>등록</Button>
        </div>
    );
}
