import { Pageable } from "./pageable";

export class Page {
    constructor(
        public content: Array<any>,
        public empty: boolean,
        public first: boolean,
        public last: boolean,
        public number: number,
        public numberOfElements: number,
        public size: number,
        public totalElements: number,
        public totalPages: number,
        public pageable: Pageable
    ) { }
}