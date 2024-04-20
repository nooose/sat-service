import { create } from 'zustand'
import CategoryCreateRequest from "@/model/request/CategoryCreateRequest";
import CommentCreateRequest from "@/model/request/CommentCreateRequest";

const commentCreateStore = create<CommentCreateRequest>((set) => ({
    content: "",
    parentId: null,
    setContent: (content: string) => set(() => ({ content: content })),
    setParentId: (parentId: number) => set(() => ({ parentId: parentId })),
}))

export default commentCreateStore
