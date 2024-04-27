"use client"

import {Button} from "@nextui-org/react";
import {useRouter} from "next/navigation";

export default function ClientArticleUpdateButton({id}: { id: number }) {
    const router = useRouter();
    const onClick = () => {
        router.push(`/articles/${id}/edit`);
    }

    return (
        <div>
            <Button
                color="primary"
                onClick={onClick}
            >수정하기</Button>
        </div>
    );
}
