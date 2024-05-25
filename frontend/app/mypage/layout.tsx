import React from "react";
import SideMenu from "@/components/mypage/menu";

export default function SideNavigation({children}: { children: React.ReactNode }) {
    return (
        <div className="flex gap-20">
            <div style={{height: '100vh'}}>
                <SideMenu/>
            </div>
            <div>
                {children}
            </div>
        </div>
    );
}
