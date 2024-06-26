package com.sat.common.config.jpa

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

data class JpqlLimit<T : Any>(
    val selectQuery: QueryPart,
    val limit: Long,
    override val returnType: KClass<T>,
) : SelectQuery<T>

@Component
class JpqlLimitSerializer : JpqlSerializer<JpqlLimit<*>> {
    override fun handledType(): KClass<JpqlLimit<*>> {
        return JpqlLimit::class
    }
    override fun serialize(
        part: JpqlLimit<*>,
        writer: JpqlWriter,
        context: RenderContext,
    ) {
        val delegate = context.getValue(JpqlRenderSerializer)
        delegate.serialize(part.selectQuery, writer, context)
        writer.write(" LIMIT ${part.limit}")
    }
}

inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.limit(limit: Int): JpqlLimit<T> {
    return JpqlLimit(
        this.toQuery(),
        limit.toLong(),
        T::class,
    )
}

fun <T : Any> KotlinJdslJpqlExecutor.findOne(init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): T? {
    try {
        CurrentFunNameHolder.funName = Thread.currentThread().callerName
        val result = this.findAll(Jpql, init).filterNotNull()
        check(result.size < 2) { "다건 조회가 발생했습니다." }
        return result.firstOrNull()
    } finally {
        CurrentFunNameHolder.clear()
    }
}

fun <T : Any> KotlinJdslJpqlExecutor.findNotNullAll(init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): List<T> {
    try {
        CurrentFunNameHolder.funName = Thread.currentThread().callerName
        return this.findAll(Jpql, init).filterNotNull()
    } finally {
        CurrentFunNameHolder.clear()
    }
}

fun <T : Any> KotlinJdslJpqlExecutor.findNotNullPage(pageable: Pageable, init: Jpql.() -> JpqlQueryable<SelectQuery<T>>): PageImpl<T> {
    try {
        CurrentFunNameHolder.funName = Thread.currentThread().callerName
        val page = this.findPage(Jpql, pageable, init)
        return PageImpl(page.filterNotNull(), pageable, page.totalElements)
    } finally {
        CurrentFunNameHolder.clear()
    }
}

private val Thread.callerName: String
    get() {
        val stack = this.stackTrace[3]
        return "${stack.className}-${stack.methodName}"
    }
