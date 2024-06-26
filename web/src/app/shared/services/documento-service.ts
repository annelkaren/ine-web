import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PATH_BY_CASILLAS, PATH_DOCUMENTO, PATH_DOCUMENTO_COUNTER, PATH_DOCUMENTO_FILTERS, PATH_FORMULARIO, PATH_FORMULARIO_GRAPHIC, URL_HOST } from "../constants/api.url";
import { Page } from "../api-objects/page";
import { PARAM_KEY, PARAM_PAGINATOR, PARAM_FILTER } from "../constants/constants";
import { Counter } from "../api-objects/counter";
import { Documento } from "../api-objects/documento";
import { Data } from "../api-objects/data";
import { SimpleObject } from "../api-objects/simple-object";
import { Results } from "../api-objects/results";

@Injectable()
export class DocumentoService {
    constructor(
        private http: HttpClient
    ) { }

    public getAll(filter: number, paginator: number): Observable<Page> {
        let params = new HttpParams();
        params = params.append(PARAM_FILTER, filter);
        params = params.append(PARAM_PAGINATOR, paginator);
        params = params.append("size", 32);
        return this.http.get<Page>(URL_HOST + PATH_DOCUMENTO, {
            params
        })
    }

    public geyByFilters(filter: number, key: string, paginator: number): Observable<Page> {
        let params = new HttpParams();
        params = params.append(PARAM_FILTER, filter);
        params = params.append(PARAM_KEY, key);
        params = params.append(PARAM_PAGINATOR, paginator);
        return this.http.get<Page>(URL_HOST + PATH_DOCUMENTO + PATH_DOCUMENTO_FILTERS, {
            params
        });
    }

    public counter(): Observable<Counter> {
        return this.http.get<Counter>(URL_HOST + PATH_DOCUMENTO + PATH_DOCUMENTO_COUNTER)
    }

    public save(documento: Documento): Observable<Response> {
        return this.http.post<Response>(URL_HOST + PATH_DOCUMENTO, documento);
    }


    public getById(id: number): Promise<Documento> {
        return new Promise(resolve => {
            this.http.get<Documento>(URL_HOST + PATH_DOCUMENTO + "/" + id)
                .subscribe(response => {
                    resolve(response);
                },
                    () => {
                        resolve(undefined);
                    });
        });
    }

    public graphic(): Observable<Data> {
        return this.http.get<Data>(URL_HOST + PATH_FORMULARIO + PATH_FORMULARIO_GRAPHIC);
    }

    public saveFile(clave: string, file: any): Observable<String> {
        const formData = new FormData();
        formData.append("clave", clave);
        formData.append("file", file);
        return this.http.post<String>(URL_HOST + PATH_DOCUMENTO + "/files", formData);
    }

    public getFile(filename: string): Observable<SimpleObject> {
        return this.http.get<SimpleObject>(URL_HOST + PATH_DOCUMENTO + "/file/" + filename);
    }

    public casillas(): Observable<Results> {
        return this.http.get<Results>(URL_HOST + PATH_FORMULARIO + PATH_BY_CASILLAS);
    }
}