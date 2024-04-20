import {Link, NavbarItem} from "@nextui-org/react";

export default function User({ username, authenticated }: { username: string; authenticated: boolean }) {
    return (
        <div>
            {authenticated && (
                <NavbarItem className="hidden lg:flex">
                    <span>{username}</span>
                </NavbarItem>
            )}
            {!authenticated && (
                <NavbarItem className="hidden lg:flex">
                    <Link href="http://localhost:8080/oauth2/authorization/kakao">로그인</Link>
                </NavbarItem>
            )}
        </div>
    );
}