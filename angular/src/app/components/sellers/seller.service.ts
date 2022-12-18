import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Seller} from "./seller.model";
import {APP_BASE_URL} from "../../app.service";

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  private sellersUrl = APP_BASE_URL + '/api/sellers';

  constructor(private http: HttpClient) {
  }

  public findSellerById(id: any): Observable<any> {
    return this.http.get<Seller>(`${this.sellersUrl}/${id}`, id);
  }

  public findSellerByUserLogged(): Observable<Seller> {
    return this.http.get<Seller>(`${this.sellersUrl}/user-logged`);
  }

  public saveNewSeller(): Observable<Seller> {
    return this.http.post<Seller>(`${this.sellersUrl}`, null);
  }

}
