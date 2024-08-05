package com.sat.common.config.jpa

import com.linecorp.kotlinjdsl.querymodel.QueryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.writer.JpqlWriter
import io.github.oshai.kotlinlogging.KotlinLogging
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

private val log = KotlinLogging.logger { }

inline fun <reified T : Any> JpqlQueryable<SelectQuery<T>>.limit(limit: Int): JpqlLimit<T> {
    return JpqlLimit(
        this.toQuery(),
        limit.toLong(),
        T::class,
    )
}
