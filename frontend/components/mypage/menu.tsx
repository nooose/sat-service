"use client"

import React from "react";
import {Link} from "@nextui-org/react";
import {usePathname, useRouter} from "next/navigation";

export default function SideMenu() {
    const path = usePathname();
    const router = useRouter();

    const menus = [
        { href: "/mypage/info", text: "정보" },
        { href: "/mypage/articles", text: "게시글" },
        { href: "/mypage/comments", text: "댓글" },
        { href: "/mypage/points", text: "포인트" },
        { href: "/mypage/likes", text: "좋아요" }
    ];

    return (
        <div className="flex flex-col gap-2">
            {menus.map((link, index) => (
                <Link
                    key={index}
                    onClick={() => {
                        router.push(link.href);
                        router.refresh();
                    }}
                    color={path === link.href ? "primary" : "foreground"}
                    style={{
                        fontWeight: path === link.href ? "bold" : "normal",
                        cursor: "pointer",
                    }}
                >
                    {link.text}
                </Link>
            ))}
        </div>
    );
}
