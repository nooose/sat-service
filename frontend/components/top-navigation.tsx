"use client"

import React from "react";
import { usePathname } from "next/navigation";
import {Navbar, NavbarBrand, NavbarContent, NavbarItem, Link, Button} from "@nextui-org/react";

export default function Navigation() {
  const path = usePathname();

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
        <NavbarItem className="hidden lg:flex">
          <Link href="#">로그인</Link>
        </NavbarItem>
        <NavbarItem>
          <Button as={Link} color="primary" href="#" variant="flat">
            가입
          </Button>
        </NavbarItem>
      </NavbarContent>
    </Navbar>
  );
}
