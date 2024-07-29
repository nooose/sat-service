import React from "react";
import {cookies} from "next/headers";
import {RestClient} from "@/utils/rest-client";
import PointRanking from "@/components/point/pointRanking";


async function getPointRankings() {
    const cookie = cookies().get("JSESSIONID")?.value
    const response = await RestClient.get("/user/point-ranking")
        .session(cookie)
        .fetch();
    return await response.json();
}


export default async function pointRankingPage() {
    const pointRankings = await getPointRankings();
    return (
        <div>
            <PointRanking
                pointRankings={pointRankings}
            />
        </div>
    );
}
