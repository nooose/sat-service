package com.sat.event.domain

import com.sat.event.ui.NotificationEventHandler

/**
 * @see NotificationEventHandler
 */
data class NotificationEvent<T>(
    val targetMemberId: Long,
    val notification: T
)
