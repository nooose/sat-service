package com.sat.chat.domain

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRoomRepository : MongoRepository<ChatRoom, ObjectId>
