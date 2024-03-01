package com.sat.study.domain

import jakarta.persistence.*

@Entity
class StudyGroup(
    var title: String,
    var content: String,
    @Embedded
    var period: Period,
    var status: Status,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "studyGroup", orphanRemoval = true)
    val participants: MutableList<Participant> = mutableListOf(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : AuditingFields() {

    init {
        require(participants.count { it.isHost() } == 1) { "스터디그룹 호스트는 1명이어야 합니다." }
        participants.forEach { it.studyGroup = this }
    }

    fun add(participant: Participant) {
        require(!participant.isHost()) { "호스트를 더 이상 추가할 수 없습니다." }
        participants.add(participant)
    }

    companion object {
        fun open(
            title: String,
            content: String,
            period: Period,
            host: Participant,
        ): StudyGroup {
            return StudyGroup(
                title = title,
                content = content,
                period = period,
                status = Status.RECRUITING,
                participants = mutableListOf(host)
            )
        }
    }
}
