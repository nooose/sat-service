import {cookies} from "next/headers";
import {getUserInfo} from "@/components/user-login";
import React from "react";
import ClientMypageInfoUpdate from "@/components/mypage/client-mypage-info-update";
import {Input} from "@nextui-org/input";
import styles from "@styles/mypage.module.css";


export default async function MyPageInfo() {
    const cookie = cookies().get("JSESSIONID")?.value
    const member = await getUserInfo(cookie);
    return (
        <div>
            <h1>내 정보</h1>
            <ClientMypageInfoUpdate nickname={member.nickname}/>
            <Input type="text"
                   label="이메일"
                   disabled={true}
                   defaultValue={member.email}
            />
        </div>
    );
}
