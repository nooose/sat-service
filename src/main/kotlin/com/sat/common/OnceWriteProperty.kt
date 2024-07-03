package com.sat.common

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class OnceWriteProperty<T>(
    private var value: T,
) : ReadWriteProperty<Any?, T> {
    private var touched = false

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        require(!touched) { "${property.name} 더 이상 초기화할 수 없습니다." }
        touched = true
        this.value = value
    }
}
