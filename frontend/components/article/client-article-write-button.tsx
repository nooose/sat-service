"use client"

import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export default function ClientArticleWriteButton(){
    const router = useRouter();
    const onClick = () => {
        router.push("/articles/write");
    }

    return (
        <div>
            <Button
                color = "primary"
                onClick={onClick}
            >글 쓰기</Button>
        </div>
    );
}
