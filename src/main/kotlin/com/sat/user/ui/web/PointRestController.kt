package com.sat.user.ui.web

import com.sat.user.query.PointQuery
import com.sat.user.query.PointQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PointRestController(
    private val pointQueryService: PointQueryService,
) {

   @GetMapping("/user/points/ranking")
   fun getPointRanking(): List<PointQuery> {
       return pointQueryService.getPointRanking()
   }
}
