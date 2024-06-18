import React from "react";
import SideMenu from "@/components/mypage/menu";
import MemberSideMenu from "@/components/member/member-menu";

export default function SideNavigation({ children, params }: { children: React.ReactNode, params: any }) {
    return (
        <div className="flex gap-20">
            <div style={{height: '100vh'}}>
                <MemberSideMenu memberId={params.id} />
            </div>
            <div>
                {children}
            </div>
        </div>
    );
}
