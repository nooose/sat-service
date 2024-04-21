class SatUser {
    id: number;
    name: string;
    nickname: string;
    email: string;

    constructor(id: number, name: string, nickname: string, email: string) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
    }

    static unauthenticated(): SatUser {
        return new SatUser(0, "", "", "");
    }

    isAuthenticated(): boolean {
        return !!this.name;
    }
}

export default SatUser;
