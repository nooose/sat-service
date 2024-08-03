package com.sat.user.query

class PointRankingWithMember(
    pointRankings: List<PointQuery>,
    members: List<MemberSimpleQuery>,
) {
    val pointRankings: List<PointQuery>

    init {
        val memberMap = members.associateBy { it.id }
        this.pointRankings = pointRankings.map {
            it.apply { memberName = memberMap[memberId]?.name }
        }
    }
}
