const API_HOST = "http://localhost:8080";
const DEFAULT_HEADERS = {
    'Accept': 'application/json',
};

export async function httpClient(path: string, method = 'GET', body = null) {
    const url = `${API_HOST}${path}`;
    const config = {
        method,
        headers: DEFAULT_HEADERS,
        body: body ? JSON.stringify(body) : null,
    };

    return await fetch(url, config);
}