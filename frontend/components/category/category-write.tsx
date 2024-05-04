"use client"

import {Input} from "@nextui-org/input";
import {Button} from "@nextui-org/react";
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";
import {useState} from "react";
import {useRouter} from "next/navigation";
import {RestClient} from "@/utils/restClient";

export default function CategoryWrite() {
    const [name, setName] = useState('');
    const [parentId, setParentId] = useState(null);
    const router = useRouter();

    const saveCategory = () => {
        console.log("머야");
        const request: CategoryCreateRequest = {
            name: name,
            parentId: parentId,
        }
        RestClient.post("/board/categories")
            .requestBody(request)
            .successHandler(() => {
                router.push('/category');
                router.refresh();
            }).fetch();
    }

    return (
        <div>
            <Input type="text" label="카테고리 명" placeholder="카테고리 명을 입력해 주세요"
                   onChange={event => setName(event.target.value)}
            />
            <Button color="primary" onClick={saveCategory}>등록</Button>
        </div>
    );
}
