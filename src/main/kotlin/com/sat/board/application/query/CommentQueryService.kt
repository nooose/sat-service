package com.sat.board.application.query

import com.sat.board.application.query.dto.CommentHierarchy
import com.sat.board.application.query.dto.CommentQuery
import com.sat.board.domain.dto.CommentWithMemberDto
import com.sat.board.domain.port.ArticleRepository
import com.sat.board.domain.port.CommentRepository
import com.sat.common.domain.exception.NotFoundException
import com.sat.user.domain.dto.CommentDto
import com.sat.user.domain.port.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommentQueryService(
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository,
    private val memberRepository: MemberRepository,
) {
    fun get(articleId: Long): List<CommentQuery> {
        if (!articleRepository.existsById(articleId)) {
            throw NotFoundException("게시글이 존재하지 않습니다. - $articleId")
        }

        val comments = commentRepository.findAll(articleId)
        val memberIds = comments.map { it.createdBy!! }
        val members = memberRepository.findAllById(memberIds)
        val memberMap = members.associateBy { it.id }

        val commentWithMember = comments.map {
            val member = memberMap[it.createdBy]!!
            CommentWithMemberDto.from(it, member.nickname)
        }
        val hierarchy = CommentHierarchy(commentWithMember)
        return hierarchy.comments
    }

    fun getComments(memberId: Long): List<CommentDto> {
        val commentWitArticle = commentRepository.findByCreatedBy(memberId)
        return commentWitArticle.map { CommentDto.from(it) }
    }
}
