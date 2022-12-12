import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../items/items.model';
import { Cart } from './cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) { }

  public findCartForUserLogged(): Observable<Cart[]>{
    return this.http.get<Cart[]>(`${this.cartUrl}/user`);
  }

  public addItemToCartForUser(id: any, amountSelected: number) {
    return this.http.post<Item>(`${this.cartUrl}/item-add?id=${id}&amountSelected=${amountSelected}`, id);
  }

  public updateCartForUser(cart: Cart) {
    return this.http.put<Cart>(`${this.cartUrl}`, cart);

  }

  public deleteItemFromCart(id: any) {
    return this.http.delete<Item>(`${this.cartUrl}/${id}`, id);
  }

  public deleteItemFromAllCarts(id: any) {
    return this.http.delete<Item>(`${this.cartUrl}/all/${id}`, id);
  }


}
