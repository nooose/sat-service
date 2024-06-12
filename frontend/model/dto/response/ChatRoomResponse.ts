interface ChatRoomResponse {
    id: string,
    name: string,
    maximumCapacity: number,
    memberCount?: number,
    ownerId: number,
}

interface ChatRoomOccupancyQuery {
    id: string,
    onlineMemberCount: number,
}
