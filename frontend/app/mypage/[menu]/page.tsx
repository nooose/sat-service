import MyPageArticles from "@/components/mypage/mypage-articles";
import MyPageComments from "@/components/mypage/mypage-comments";

export async function generateMetadata({params: {menu}}: any) {
    return {
        title: menu,
    };
}

export default async function MyPageMenu({params: {menu}}: any) {
    return (
        <div>
            {menu === "info" && <h1>mypage</h1>}
            {menu === "point" && <h1>point</h1>}
            {menu === "articles" && <MyPageArticles/>}
            {menu === "comments" && <MyPageComments/>}
        </div>
    );
}
