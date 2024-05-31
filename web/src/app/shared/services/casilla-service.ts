import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { PATH_CASILLA, URL_HOST } from "../constants/api.url";
import { Casilla } from "../api-objects/casilla";
import { Observable } from "rxjs";

@Injectable()
export class CasillaService {
    constructor(
        private http: HttpClient
    ) { }

    public counter(): Observable<Array<Casilla>> {
        return this.http.get<Array<Casilla>>(URL_HOST + PATH_CASILLA)
    }
}