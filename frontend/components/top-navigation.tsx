import React from "react";
import {Link, Navbar, NavbarBrand, NavbarContent, NavbarItem} from "@nextui-org/react";
import UserLogin from "@/components/user-login";

export default function Navigation() {
    return (
        <Navbar shouldHideOnScroll>
            <NavbarBrand>
                <a href="/" className="font-bold text-inherit">S.A.T</a>
            </NavbarBrand>
            <NavbarContent className="hidden sm:flex gap-4" justify="center">
                <NavbarItem>
                    <Link href="/category">
                        카테고리
                    </Link>
                </NavbarItem>
                <NavbarItem>
                    <Link href="/menu2">
                        메뉴2
                    </Link>
                </NavbarItem>
            </NavbarContent>
            <NavbarContent justify="end">
                <UserLogin/>
            </NavbarContent>
        </Navbar>
    );
}
