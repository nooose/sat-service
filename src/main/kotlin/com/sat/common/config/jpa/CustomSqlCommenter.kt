package com.sat.common.config.jpa

import org.hibernate.resource.jdbc.spi.StatementInspector


class CustomSqlCommenter : StatementInspector {
    override fun inspect(sql: String): String {
        val currentFunction = CurrentFunNameHolder.funName
        if (currentFunction.isNullOrBlank()) {
            return sql
        }

        return "/* ${CurrentFunNameHolder.funName} */ $sql"
    }
}
