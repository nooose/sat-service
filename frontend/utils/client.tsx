const API_HOST = "http://localhost:8080";
const DEFAULT_HEADERS = {
    'Accept': 'application/json',
};

async function fetch(path: string, method = 'GET', body = null) {
    const url = `${API_HOST}${path}`;
    const config = {
        method,
        headers: DEFAULT_HEADERS,
        body: body ? JSON.stringify(body) : null,
    };

    const response = await fetch(url, config);
    return await response.json();
}