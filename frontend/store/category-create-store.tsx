import { create } from 'zustand'
import CategoryCreateRequest from "@/model/dto/request/CategoryCreateRequest";

const categoryCreateStore = create<CategoryCreateRequest>((set) => ({
    name: "",
    parentId: null,
    parentName: null,
    setName: (name: string) => set(() => ({ name: name })),
    setParentId: (parentId: number) => set(() => ({ parentId: parentId })),
    setParentName: (parentName: string) => set(() => ({ parentName: parentName })),
}))

export default categoryCreateStore
