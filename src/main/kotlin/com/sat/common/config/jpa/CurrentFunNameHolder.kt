package com.sat.common.config.jpa

internal object CurrentFunNameHolder {
    private val methodNameHolder: ThreadLocal<String> = ThreadLocal()

    var funName: String?
        get() = methodNameHolder.get()
        set(value) = if (methodNameHolder.get() == null) methodNameHolder.set(value) else Unit

    fun clear() {
        methodNameHolder.remove()
    }
}
