class SatUser {
    id: number;
    name: string;
    nickname: string;
    email: string;
    avatar: string;

    constructor(id: number, name: string, nickname: string, email: string, avatar: string) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
    }

    static unauthenticated(): SatUser {
        return new SatUser(0, "", "", "", "");
    }

    isAuthenticated(): boolean {
        return !!this.name;
    }
}

export default SatUser;
