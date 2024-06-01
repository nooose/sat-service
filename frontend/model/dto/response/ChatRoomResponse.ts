import ChatCreateRequest from "@/model/dto/request/ChatCreateRequest";

interface ChatRoomResponse {
    id: string,
    name: string,
    owner: string,
}

export default ChatRoomResponse;