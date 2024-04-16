export class Documento {
    constructor(
        public id: number | undefined,
        public version: number,
        public clave: string,
        public url: string, 
        public estatus: string, 
        public isVoice: boolean
    ) { }
}