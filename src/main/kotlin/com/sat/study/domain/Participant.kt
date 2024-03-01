package com.sat.study.domain

import com.sat.study.domain.ParticipantType.HOST
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Participant(
    val memberId: Long,
    val type: ParticipantType,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne @JoinColumn(name = "study_group_id")
    var studyGroup: StudyGroup? = null,
) {

    fun isHost(): Boolean {
        return type == HOST
    }

    companion object {
        fun host(hostId: Long): Participant {
            return Participant(
                memberId = hostId,
                type = HOST,
            )
        }
    }
}
