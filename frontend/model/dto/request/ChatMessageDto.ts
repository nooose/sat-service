interface ChatMessageRequest {
    content: string,
}

interface ChatMessageResponse {
    senderId: number,
    senderName: string,
    content: string,
    createdDateTime: string,
}

interface ChatUser {
    sessionId: string,
    name: string,
}
