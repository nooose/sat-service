import {cookies} from "next/headers";
import React from "react";
import {Input} from "@nextui-org/input";
import styles from "@styles/mypage.module.css";
import {RestClient} from "@/utils/rest-client";
import ClientMemberInfoNickname from "@/components/member/client-member-info-nickname";

async function findMember(memberId: number) {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get(`/user/members/${memberId}`)
        .session(cookie)
        .fetch();
    return await response.json();
}


export default async function MemberInfo({memberId}: {memberId: number}) {
    const member = await findMember(memberId);

    return (
        <div className={styles.container}>
            <h1>내 정보</h1>
            <ClientMemberInfoNickname nickname={member.name}/>
            <Input type="text"
                   label="이메일"
                   disabled={true}
                   defaultValue={member.email}
            />
        </div>
    );
}
