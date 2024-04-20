import { create } from 'zustand'
import ArticleCreateRequest from "@/model/request/ArticleCreateRequest";

const authStore = create((set) => ({
    authenticated: false,
    name: "",
    setAuthenticated: (authenticated: boolean) => set(() => ({ authenticated: authenticated})),
    setName: (name: string) => set(() => ({ name: name })),
}))

export default authStore
