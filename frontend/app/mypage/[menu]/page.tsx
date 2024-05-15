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
            {menu === "articles" && <h1>articles</h1>}
            {menu === "comments" && <h1>comments</h1>}
        </div>
    );
}
