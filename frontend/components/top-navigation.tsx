"use client"

import React from "react";
import {usePathname} from "next/navigation";
import {Navbar, NavbarBrand, NavbarContent, NavbarItem, Link, Button} from "@nextui-org/react";
import authStore from "@/store/auth-store";
import User from "@/components/user";

export default function Navigation() {
    const path = usePathname();
    const state = authStore((state: any) => state);

    return (
        <Navbar shouldHideOnScroll>
            <NavbarBrand>
                <a href="/" className="font-bold text-inherit">S.A.T</a>
            </NavbarBrand>
            <NavbarContent className="hidden sm:flex gap-4" justify="center">
                <NavbarItem isActive={path === "/category"}>
                    <Link href="/category">
                        카테고리
                    </Link>
                </NavbarItem>
                <NavbarItem isActive={path === "/menu2"}>
                    <Link href="/menu2">
                        메뉴2
                    </Link>
                </NavbarItem>
            </NavbarContent>
            <NavbarContent justify="end">
                <User username={state.name} authenticated={state.authenticated}/>
            </NavbarContent>
        </Navbar>
    );
}
