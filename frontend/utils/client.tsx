const API_HOST = "http://localhost:8080";
const headers: Record<string, string> = {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
};

export async function get(path: string, authenticationCookie: string | null | undefined = null) {
    return await request(path, "GET", authenticationCookie);
}

export async function post(path: string, body: any | null | undefined = null, authenticationCookie: string | null | undefined = null) {
    return await request(path, "POST", authenticationCookie, body);
}

export async function put(path: string, body: any | null | undefined = null, authenticationCookie: string | null | undefined = null) {
    return await request(path, "PUT", authenticationCookie, body);
}

export async function request(path: string, method: string, authenticationCookie: string | null | undefined = null, body: any | null | undefined = null) {
    const url = `${API_HOST}${path}`;
    const config = {
        method: method,
        headers,
        body: body ? JSON.stringify(body) : null,
    };
    console.log(`>>> ${authenticationCookie}`);
    if (authenticationCookie != null || authenticationCookie != undefined) {
        headers['Cookie'] = `${headers["Cookie"]};JSESSIONID=${authenticationCookie}`;
    }
    return await fetch(url, {
        ...config,
        credentials: 'include',
    });
}
