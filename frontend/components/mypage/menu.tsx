"use client"

import React from "react";
import {Listbox, ListboxItem} from "@nextui-org/react";

export default function SideMenu() {
    return (
        <div className="flex flex-col gap-2">
            <Listbox>
                <ListboxItem key="info" href={"/mypage"}>
                    정보
                </ListboxItem>
                <ListboxItem key="articles" href={"/mypage/articles"}>
                    작성한 게시글 목록
                </ListboxItem>
                <ListboxItem key="comments" href={"/mypage/comments"}>
                    작성한 댓글 목록
                </ListboxItem>
                <ListboxItem key="point" href={"/mypage/point"}>
                    포인트
                </ListboxItem>
            </Listbox>
        </div>
    );
}
