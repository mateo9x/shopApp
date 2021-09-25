import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../items/items/items.model';
import { Cart } from './cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) { }

  public findCartForUser(id: number): Observable<Cart[]> {
    return this.http.get<Cart[]>(`${this.cartUrl}/user/${id}`);
  }
  public addItemToCartForUser(id: any) {
    return this.http.post<Item>(`${this.cartUrl}/item-add/${id}`, id);
  }

  public updateCartForUser(cart: Cart) {
    return this.http.put<Cart>(`${this.cartUrl}`, cart);

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
