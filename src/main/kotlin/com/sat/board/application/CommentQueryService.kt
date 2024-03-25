package com.sat.board.application

import com.sat.board.application.dto.query.CommentHierarchy
import com.sat.board.application.dto.query.CommentQuery
import com.sat.board.domain.dto.CommentDto
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.common.utils.findByIdOrThrow
import com.sat.user.domain.port.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommentQueryService(
    private val commentRepository: CommentRepository,
    private val articleRepository: ArticleRepository,
    private val memberRepository: MemberRepository,
) {
    fun get(articleId: Long): List<CommentQuery> {
        articleRepository.findByIdOrThrow(articleId) { "게시글이 존재하지 않습니다." }
        val comments = commentRepository.findAll(articleId)
        val memberIds = comments.map { it.createdBy!! }
        val members = memberRepository.findByIdContains(memberIds)

        val commentDtos = comments.map { CommentDto.from(it) }
        for (commentDto in commentDtos) {
            for (member in members) {
                if (commentDto.memberId == member.id) {
                    commentDto.memberName = member.nickname
                }
            }
        }

        val hierarchy = CommentHierarchy(commentDtos)

        return hierarchy.comments
    }
}
