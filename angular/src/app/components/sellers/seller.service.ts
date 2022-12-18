import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Seller} from "./seller.model";

@Injectable({
  providedIn: 'root'
})
export class SellerService {

  private sellersUrl = 'http://localhost:8080/api/sellers';

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
