import {CommonErrorResponse} from "@/model/dto/response/CommonErrorResponse";

export const API_HOST = process.env.API_HOST!;
export const HTTP_API_HOST = `http://${API_HOST}`;

export class RestClient {
    private readonly baseUrl;
    private readonly headers: Record<string, string>;
    private method: string = "";
    private path: string = "";
    private body?: any;
    private _successHandler?: Function;
    private _errorHandler: (error: CommonErrorResponse) => void;

    private constructor() {
        this.baseUrl = HTTP_API_HOST;
        this.headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        };
        this._errorHandler = (error: CommonErrorResponse) => { };
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

    static delete(path: string) {
        const client = new RestClient();
        client.method = "DELETE";
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

    public session(session?: string) {
        if (session !== undefined) {
            this.headers['Cookie'] = `${this.headers["Cookie"]};JSESSIONID=${session}`;
        }
        return this;
    }

    public successHandler(successHandler: Function) {
        this._successHandler = successHandler;
        return this;
    }

    public errorHandler(handler: (error: CommonErrorResponse) => void) {
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
                json.then((data: any) => {
                    this._errorHandler!(new CommonErrorResponse(data, response.status));
                })
            }
            return response
        });
    }
}
