import { create } from 'zustand'
import CommentCreateRequest from "@/model/dto/request/CommentCreateRequest";
import commentCreateStore from "@/store/comment-create-store";
import CommentUpdateRequest from "@/model/dto/request/CommentUpdateRequest";

const commentUpdateStore = create<CommentUpdateRequest>((set) => ({
    content: "",
    setContent: (content: string) => set(() => ({ content: content })),
}))

export default commentUpdateStore
