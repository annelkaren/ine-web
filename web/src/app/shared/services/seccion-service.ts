import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { PATH_SECCION, PATH_SECCION_ALL, URL_HOST } from "../constants/api.url";
import { Observable } from "rxjs";
import { Seccion } from "../api-objects/seccion";

@Injectable()
export class SeccionService {
    constructor(
        private http: HttpClient
    ) { }

    public getAll(): Promise<Array<Seccion>> {
        return new Promise(resolve => {
            this.http.get<Array<Seccion>>(URL_HOST + PATH_SECCION + PATH_SECCION_ALL)
                .subscribe(response => {
                    resolve(response);
                },
                    () => {
                        resolve(undefined);
                    });
        });
    }
}