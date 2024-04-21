import { create } from 'zustand'
import ArticleCreateRequest from "@/model/dto/request/ArticleCreateRequest";

const articleCreateStore = create<ArticleCreateRequest>((set) => ({
    title: "",
    content: "",
    categoryId: 0,
    setTitle: (title: string) => set(() => ({ title: title })),
    setContent: (content: string) => set(() => ({ content: content })),
    setCategoryId: (categoryId: number) => set(() => ({ categoryId: categoryId })),
}))

export default articleCreateStore
