export class ErrorResponse {
    constructor(
        public status: number,
        public error: string
    ) { }
}