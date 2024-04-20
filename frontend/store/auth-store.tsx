import { create } from 'zustand'

const authStore = create((set) => ({
    authenticated: false,
    name: "",
    setAuthenticated: (authenticated: boolean) => set(() => ({ authenticated: authenticated})),
    setName: (name: string) => set(() => ({ name: name })),
}))

export default authStore
