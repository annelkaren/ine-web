export class Documento {
    constructor(
        public id: number,
        public version: number,
        public clave: string,
        public url: string, 
        public estatus: string, 
        public validado: boolean
    ) { }
}