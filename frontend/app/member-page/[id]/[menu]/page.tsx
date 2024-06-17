import MemberInfo from "@/components/member/member-info";
import MemberArticles from "@/components/member/member-articles";
import MemberComments from "@/components/member/member-comments";

export default function MemberPage({ params }: { params: {id: number, menu: any} }) {
    console.log(params);
    return (
        <div>
            {params.menu === "info" && <MemberInfo memberId={params.id}/>}
            {params.menu === "articles" && <MemberArticles memberId={params.id}/>}
            {params.menu === "comments" && <MemberComments memberId={params.id}/>}
        </div>
    );
}
