import React from "react";
import {Link, Navbar, NavbarBrand, NavbarContent, NavbarItem} from "@nextui-org/react";
import UserLogin from "@/components/user-login";

const links = [
    { href: "/category", label: "카테고리" },
    { href: "/chat", label: "채팅" }
];

export default function Navigation() {

    return (
        <Navbar shouldHideOnScroll>
            <NavbarBrand>
                <a href="/" className="font-bold text-inherit">S.A.T</a>
            </NavbarBrand>
            <NavbarContent className="hidden sm:flex gap-4" justify="center">
                {links.map((link, index) => (
                    <NavbarItem key={index}>
                        <Link href={link.href}>
                            {link.label}
                        </Link>
                    </NavbarItem>
                ))}
            </NavbarContent>
            <NavbarContent justify="end">
                <UserLogin/>
            </NavbarContent>
        </Navbar>
    );
}
