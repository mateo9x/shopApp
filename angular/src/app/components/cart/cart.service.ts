import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) { }

  public findCartForUser(): Observable<any[]> {
    return this.http.get<any[]>(`${this.cartUrl}`);
  }

//   public findUser(id: any) {
//     return this.http.get<User>(`${this.baseUrl}/`, id);
//   }

//   public deleteUser(id: any) {
//     return this.http.delete<User>(`${this.baseUrl}/`, id);
//   }

//   public saveUser(user: User) {
//     return this.http.post<User>(`${this.baseUrl}`, user);
//   }

  
}
