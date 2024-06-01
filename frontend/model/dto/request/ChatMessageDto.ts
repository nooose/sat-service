interface ChatMessageRequest {
    senderId: string,
    text: string,
}

interface ChatMessageResponse {
    senderId: string,
    text: string,
    createdDateTime: string,
}
