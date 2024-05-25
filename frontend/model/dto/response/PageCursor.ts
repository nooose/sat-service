import CursorRequest from "@/model/dto/response/CursorRequest";

interface PageCursor<T> {
    nextCursor: CursorRequest
    data: T;
}

export default PageCursor;
