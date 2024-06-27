import React from "react";
import {Link, Navbar, NavbarBrand, NavbarContent, NavbarItem} from "@nextui-org/react";
import UserLogin, {getUserInfo} from "@/components/user-login";
import {cookies} from "next/headers";

const links = [
    { href: "/category", label: "카테고리", isAdminOnly: true },
    { href: "/chat", label: "채팅", isAdminOnly: false },
];

export default async function Navigation() {
    const cookie = cookies().get("JSESSIONID")?.value
    const userInfo = await getUserInfo(cookie);
    const filteredLinks = links.filter(link => userInfo.isAdmin || !link.isAdminOnly);

    return (
        <Navbar shouldHideOnScroll>
            <NavbarBrand>
                <a href="/" className="font-bold text-inherit">S.A.T</a>
            </NavbarBrand>
            <NavbarContent className="hidden sm:flex gap-4" justify="center">
                {filteredLinks.map((link, index) => (
                    <NavbarItem key={index}>
                        <Link href={link.href}>
                            {link.label}
                        </Link>
                    </NavbarItem>
                ))}
            </NavbarContent>
            <NavbarContent justify="end">
                <UserLogin userInfo={userInfo}/>
            </NavbarContent>
        </Navbar>
    );
}
