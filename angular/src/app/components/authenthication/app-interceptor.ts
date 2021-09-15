import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs/internal/Observable";
import { LocalStorageService } from "./local-storage.service";
@Injectable()
export class AppInterceptor implements HttpInterceptor {

    constructor(private localStorageService: LocalStorageService) { }

    intercept(request: HttpRequest<any>,
        next: HttpHandler): Observable<HttpEvent<any>> {

        const idToken = this.localStorageService.get("id_token");

        if (!!idToken) {
           request = request.clone({
               setHeaders: {
                   Authorization: 'Bearer ' + idToken
               }
           });
        }
        return next.handle(request);
    }
}