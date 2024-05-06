"use client"

import {Button} from "@nextui-org/react";
import {HeartIcon} from "@/components/article/heart-icon";
import {RestClient} from "@/utils/restClient";
import {useState} from "react";

export default function ArticleLikeButton({id, hasLike}: {id: number, hasLike: boolean}) {
    const [likeToggle, setLikeToggle] = useState(hasLike)

    const goLike = () => {
        setLikeToggle(!likeToggle);
        RestClient.post(`/board/articles/${id}:like`)
            .fetch();
    }

    return (
        <div>
            <Button isIconOnly color={likeToggle ? "danger" : "default"} aria-label="좋아요"
            onClick={goLike}>
                <HeartIcon/>
            </Button>
        </div>
    );
}
