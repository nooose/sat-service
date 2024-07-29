package com.sat.user.query

class PointRankingWithMemberNameDto(
    pointRankings: List<PointQuery>,
    members: List<MemberSimpleQuery>,
) {
    private val _pointRankings: MutableList<PointQuery> = mutableListOf()

    val pointRankings: List<PointQuery>
        get() = _pointRankings.toList()

    init {
        val groupedMembers = pointRankings.associateBy { it.memberId }
        members.map { groupedMembers[it.id]!!.memberName = it.name }
        _pointRankings.addAll(groupedMembers.values)
    }
}
