package com.sat.study.domain

import com.sat.study.domain.ParticipantType.HOST
import com.sat.study.domain.ParticipantType.WAITING
import jakarta.persistence.*

@Entity
class Participant(
    val memberId: Long,
    @Enumerated(EnumType.STRING)
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

        fun candidate(hostId: Long): Participant {
            return Participant(
                memberId = hostId,
                type = WAITING,
            )
        }
    }
}
