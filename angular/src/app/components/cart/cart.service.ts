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

  public addItemToCartForUser(id: any) {
    return this.http.post<Item>(`${this.cartUrl}/item-add/${id}`, id);
  }

  public updateCartForUser(cart: Cart) {
    return this.http.put<Cart>(`${this.cartUrl}`, cart);

  }

  public deleteItemFromCart(id: any) {
    return this.http.delete<Item>(`${this.cartUrl}/${id}`, id);
  }

}
