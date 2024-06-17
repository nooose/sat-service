"use client"

import React from "react";
import {Link} from "@nextui-org/react";
import {usePathname, useRouter} from "next/navigation";


export default function MemberSideMenu({ memberId }: { memberId: number }) {
    const path = usePathname();
    const router = useRouter();

    const menus = [
        {href: `/member-page/${memberId}/info`, text: "정보"},
        {href: `/member-page/${memberId}/articles`, text: "게시글"},
        {href: `/member-page/${memberId}/comments`, text: "댓글"},
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
