interface ChatMessageRequest {
    text: string,
}

interface ChatMessageResponse {
    senderId: number,
    senderName: string,
    text: string,
    createdDateTime: string,
}

interface ChatUser {
    sessionId: string,
    name: string,
}
