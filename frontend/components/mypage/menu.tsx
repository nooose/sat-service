"use client"

import React from "react";
import {Link} from "@nextui-org/react";
import {usePathname} from "next/navigation";

export default function SideMenu() {
    const path = usePathname();

    const menus = [
        { href: "/mypage/info", text: "정보" },
        { href: "/mypage/articles", text: "게시글" },
        { href: "/mypage/comments", text: "댓글" },
        { href: "/mypage/point", text: "포인트" }
    ];

    return (
        <div className="flex flex-col gap-2">
            {menus.map((link, index) => (
                <Link
                    key={index}
                    href={link.href}
                    color={path === link.href ? "primary" : "foreground"}
                    style={{
                        fontWeight: path === link.href ? "bold" : "normal",
                    }}
                >
                    {link.text}
                </Link>
            ))}
        </div>
    );
}
