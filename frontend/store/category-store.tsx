import { create } from 'zustand'
import ArticleCreateRequest from "@/model/request/ArticleCreateRequest";
import categoryCreateRequest from "@/model/request/RootCategoryCreateRequest";

const categoryCreateStore = create<categoryCreateRequest>((set) => ({
    name: "",
    parentId: 0,
    setName: (name: string) => set(() => ({ name: name })),
    setParentId: (parentId: number) => set(() => ({ parentId: parentId })),
}))

export default categoryCreateStore
