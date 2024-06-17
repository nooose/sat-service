package com.sat.chat.domain

interface MessageClient {

    fun publish(destination: String, payload: Any)

    fun exit(destination: String, sessionId: String)
}
