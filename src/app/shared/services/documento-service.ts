import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { PATH_DOCUMENTO, PATH_DOCUMENTO_COUNTER, PATH_DOCUMENTO_FILTERS, URL_HOST } from "../constants/api.url";
import { Page } from "../api-objects/page";
import { PARAM_KEY, PARAM_PAGINATOR, PARAM_FILTER } from "../constants/constants";
import { Counter } from "../api-objects/counter";

@Injectable()
export class DocumentoService {
    constructor(
        private http: HttpClient
    ) { }

    public getAll(filter: number, paginator: number): Observable<Page> {
        let params = new HttpParams();
        params = params.append(PARAM_FILTER, filter);
        params = params.append(PARAM_PAGINATOR, paginator);
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
}