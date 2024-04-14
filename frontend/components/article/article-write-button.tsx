"use client"

import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export default function ArticleWriteButton(){
    const router = useRouter();
    const onClick = () => {
        router.push("/articles/write");
    }

    return (
        <div>
            <Button onClick={onClick}>글 쓰기</Button>
        </div>
    );
}
