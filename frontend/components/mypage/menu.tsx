"use client"

import React from "react";
import {Link} from "@nextui-org/react";
import {usePathname} from "next/navigation";

export default function SideMenu() {
    const path = usePathname();

    return (
        <div className="flex flex-col gap-2">
            <Link href="/mypage/info" color="foreground" underline={path === "/mypage/info" ? "always" : "none"}>정보</Link>
            <Link href="/mypage/articles" color="foreground" underline={path === "/mypage/articles" ? "always" : "none"}>내가 쓴 게시글 목록</Link>
            <Link href="/mypage/comments" color="foreground" underline={path === "/mypage/comments" ? "always" : "none"}>내가 쓴 댓글 목록</Link>
            <Link href="/mypage/point" color="foreground" underline={path === "/mypage/point" ? "always" : "none"}>포인트</Link>
        </div>
    );
}
