import MyPageArticles from "@/components/mypage/mypage-articles";
import MyPageComments from "@/components/mypage/mypage-comments";
import MyPagePoints from "@/components/mypage/mypage-points";
import MyPageInfo from "@/components/mypage/mypage-info";

export async function generateMetadata({params: {menu}}: any) {
    return {
        title: menu,
    };
}

export default async function MyPageMenu({params: {menu}}: any) {
    return (
        <div>
            {menu === "info" && <MyPageInfo/>}
            {menu === "points" && <MyPagePoints/>}
            {menu === "articles" && <MyPageArticles/>}
            {menu === "comments" && <MyPageComments/>}
        </div>
    );
}
