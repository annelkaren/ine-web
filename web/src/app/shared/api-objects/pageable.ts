export class Pageable {
    constructor(
        public offset: number,
        public pageNumber: number,
        public pageSize: number,
        public paged: boolean
    ) { }
}