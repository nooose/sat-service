class SatUser {
    id: number;
    name: string;
    nickname: string;
    email: string;
    avatar: string;
    point: number;
    isAdmin: boolean;

    constructor(id: number, name: string, nickname: string, email: string, avatar: string, point: number, isAdmin: boolean) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
        this.point = point;
        this.isAdmin = isAdmin;
    }

    static unauthenticated(): SatUser {
        return new SatUser(0, "", "", "", "", 0, false);
    }

    public isAuthenticated(): boolean {
        return !!this.name;
    }

    public isSameId(id: number): boolean {
        return this.id == id;
    }
}

export default SatUser;
