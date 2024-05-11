export class CommonErrorResponse {
    code?: string | undefined | null = null;
    error: any;
    status: number;

    constructor(data: any, status: number) {
        this.code = data.code;
        this.error = data.error;
        this.status = status;
    }

    public isBindingError() {
        return this.code === "BIND";
    }

    public filedErrorMessage(field: string): string {
        const errorDetail = this.error.find((e: any) => e.field === field);
        return errorDetail ? errorDetail.message : "";
    }

    public errorMessage(): string {
        return this.error.toString();
    }
}