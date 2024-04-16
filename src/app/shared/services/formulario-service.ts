import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PATH_FORMULARIO, URL_HOST } from "../constants/api.url";
import { Formulario } from "../api-objects/formulario";

@Injectable()
export class FormularioService {
    constructor(
        private http: HttpClient
    ) { }

    public save(formulario: Formulario): Observable<Response> {
        return this.http.post<Response>(URL_HOST + PATH_FORMULARIO, formulario);
    }

    public getByDocumentId(documentId: number): Promise<Formulario> {
        return new Promise(resolve => {
            this.http.get<Formulario>(URL_HOST + PATH_FORMULARIO + "/" + documentId)
                .subscribe(response => {
                    resolve(response);
                },
                    () => {
                        resolve(undefined);
                    });
        });
    }

    public update(formulario: Formulario): Observable<Response> {
        return this.http.put<Response>(URL_HOST + PATH_FORMULARIO, formulario);
    }

}