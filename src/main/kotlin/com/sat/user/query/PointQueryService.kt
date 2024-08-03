package com.sat.user.query

import com.sat.common.CursorRequest
import com.sat.common.PageCursor
import com.sat.common.config.jpa.findNotNullAll
import com.sat.common.config.jpa.findOne
import com.sat.common.config.jpa.limit
import com.sat.common.domain.RedisCacheName
import com.sat.user.command.domain.point.Point
import com.sat.user.command.domain.point.PointRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class PointQueryService(
    private val pointRepository: PointRepository,
    private val memberQueryService: MemberQueryService,
    private val redisTemplate: RedisTemplate<String, Any>,
) {

    fun getTotalPoint(memberId: Long): Int {
        return pointRepository.findOne {
            select(
                sum(Point::point)
            ).from(
                entity(Point::class)
            ).where(
                path(Point::memberId).equal(memberId)
            )
        }?.toInt() ?: 0
    }

    fun getPoints(memberId: Long, cursorRequest: CursorRequest): PageCursor<List<MyPointQuery>> {
        val points = pointRepository.findNotNullAll {
            selectNew<MyPointQuery>(
                path(Point::id),
                path(Point::point),
                path(Point::type),
                path(Point::createdDateTime),
            ).from(
                entity(Point::class)
            ).whereAnd(
                cursorRequest.id?.let { path(Point::id).lessThan(it) },
                path(Point::memberId).equal(memberId),
            ).orderBy(
                path(Point::id).desc(),
            ).limit(cursorRequest.size)
        }
        return cursorRequest.nextFrom(points)
    }

    fun getTotalPointsOfMembers(): List<MemberPointQuery> {
        return pointRepository.findNotNullAll {
            selectNew<MemberPointQuery>(
                path(Point::memberId),
                sum(path(Point::point)),
            ).from(
                entity(Point::class)
            ).groupBy(
                path(Point::memberId)
            )
        }
    }

    fun getPoint(memberId: Long): Int {
        val points = redisTemplate.opsForZSet()
        return points.score(RedisCacheName.RANKING.key, memberId)!!.toInt()
    }

    fun getPointRanking(): List<PointQuery> {
        val pointRankingZSetOps = redisTemplate.opsForZSet()
        val ranking = pointRankingZSetOps.reverseRangeWithScores(RedisCacheName.RANKING.key, 0, 9)
                ?: throw IllegalStateException("포인트 랭킹 정보를 찾을 수 없습니다.")

        val pointRankings = ranking.map { PointQuery(memberId = it.value as Long, point = it.score?.toInt() ?: 0) }
        val memberIds = pointRankings.map { it.memberId }
        val members = memberQueryService.getByIds(memberIds)

        return PointRankingWithMember(pointRankings, members).pointRankings
    }
}
