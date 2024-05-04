const API_HOST = process.env.API_HOST!;

export class RestClient {
    private readonly baseUrl;
    private readonly headers: Record<string, string>;
    private method: string = "";
    private path: string = "";
    private body: any | undefined | null;
    private _successHandler: Function | undefined;
    private _errorHandler: (message: string) => void;

    private constructor() {
        this.baseUrl = API_HOST;
        this.headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        };
        this._errorHandler = (message: string) => {
            alert(message);
        };
    }

    static get(path: string) {
        const client = new RestClient();
        client.method = "GET";
        client.path = path;
        return client;
    }

    static post(path: string) {
        const client = new RestClient();
        client.method = "POST";
        client.path = path;
        return client;
    }

    static put(path: string) {
        const client = new RestClient();
        client.method = "PUT";
        client.path = path;
        return client;
    }

    static patch(path: string) {
        const client = new RestClient();
        client.method = "PATCH";
        client.path = path;
        return client;
    }

    public requestBody(body: any) {
        this.body = body;
        return this;
    }

    public session(session: string | undefined) {
        if (session !== undefined) {
            this.headers['Cookie'] = `${this.headers["Cookie"]};JSESSIONID=${session}`;
        }
        return this;
    }

    public successHandler(successHandler: Function) {
        this._successHandler = successHandler;
        return this;
    }

    public errorHandler(handler: (message: string) => void) {
        this._errorHandler = handler;
        return this;
    }

    public async fetch() {
        const url = `${this.baseUrl}${this.path}`;
        const config = {
            method: this.method,
            headers: this.headers,
            body: this.body ? JSON.stringify(this.body) : null,
        };

        return await fetch(url, {
            ...config,
            credentials: 'include',
        }).then(response => {
            if (response.ok && this._successHandler !== undefined) {
                this._successHandler!();
            } else if (!response.ok) {
                const json = response.json();
                json.then(data => {
                    this._errorHandler!(JSON.stringify(data.error));
                })
            }
            return response
        });
    }
}
