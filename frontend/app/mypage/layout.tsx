import React from "react";
import SideMenu from "@/components/mypage/menu";

export default function SideNavigation({children}: { children: React.ReactNode }) {
    return (
        <div>
            <div style={{float: 'left', width: '200px', height: '100vh'}}>
                <SideMenu/>
            </div>
            <div>
                {children}
            </div>
        </div>
    );
}
