"use client"

import {Button} from "@nextui-org/react";
import {HeartIcon} from "@/components/article/heart-icon";
import {RestClient} from "@/utils/rest-client";
import {useState} from "react";
import {errorToast} from "@/utils/toast-utils";

export default function ArticleLikeButton({id, hasLike}: { id: number, hasLike: boolean }) {
    const [likeToggle, setLikeToggle] = useState(hasLike)

    const goLike = () => {
        RestClient.post(`/board/articles/${id}:like`)
            .successHandler(() => {
                setLikeToggle(!likeToggle);
            })
            .errorHandler(error => {
                if (error.status == 401) {
                    errorToast("로그인 후 이용해 주세요.");
                }
            })
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
