import { create } from 'zustand'
import CategoryUpdateRequest from "@/model/request/CategoryUpdateRequest";

const categoryUpdateRequest = create<CategoryUpdateRequest>((set) => ({
    name: "",
    parentId: null,
    setName: (name: string) => set(() => ({ name: name })),
    setParentId: (parentId: number) => set(() => ({ parentId: parentId })),
}))

export default categoryUpdateRequest
