package com.sat.study.ui.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.sat.study.application.StudyGroupCommandService
import com.sat.study.application.dto.command.StudyGroupCreateCommand
import com.sat.study.common.documentation.Documentation
import com.sat.study.common.documentation.dsl.POST
import com.sat.study.common.documentation.dsl.andDocument
import com.sat.study.domain.Period
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.time.LocalDate

@DisplayName(value = "API 문서화 - 스터디그룹")
@WebMvcTest(StudyGroupRestController::class)
class StudyGroupRestControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
) : Documentation() {

    @MockkBean
    lateinit var studyGroupCommandService: StudyGroupCommandService

    @Test
    fun `스터디그룹 생성`() {
        val now = LocalDate.now()
        val command = StudyGroupCreateCommand("스터디그룹 A", "내용", Period(now, now.plusDays(7)))

        every { studyGroupCommandService.create(any(), any()) } just runs

        mockMvc.POST("/study/groups") {
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "스터디 > 그룹"
            summary = "스터디그룹 생성"
            requestBody {
                field("title", "스터디그룹 제목")
                field("content", "스터디그룹 내용")
                field("period.startDate", "스터디그룹 시작일")
                field("period.endDate", "스터디그룹 종료일")
            }
        }
    }

    @Test
    fun `스터디그룹 참여 요청`() {
        every { studyGroupCommandService.join(any(), any()) } just runs

        mockMvc.POST("/study/groups/{groupId}/participants:request", 1) {
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "utf-8"
        }.andExpect {
            status { isOk() }
        }.andDocument {
            tag = "스터디 > 그룹"
            summary = "스터디그룹 생성"
            pathVariables {
                param("groupId", "스터디그룹 ID")
            }
        }
    }

}
