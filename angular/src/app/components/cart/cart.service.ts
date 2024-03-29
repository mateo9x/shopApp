import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../items/items.model';
import {Cart, CartUpdateRequest} from './cart.model';
import {APP_BASE_URL} from "../../app.service";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = APP_BASE_URL + '/api/cart';

  constructor(private http: HttpClient) { }

  public findCartItemsForUserLogged(): Observable<Cart[]>{
    return this.http.get<Cart[]>(`${this.cartUrl}/user`);
  }

  public addItemToCartForUser(id: any, amountSelected: number) {
    return this.http.post<Item>(`${this.cartUrl}/item-add?id=${id}&amountSelected=${amountSelected}`, id);
  }

  public deleteItemFromCart(id: any) {
    return this.http.delete<Item>(`${this.cartUrl}/${id}`, id);
  }

  public updateItemAmountCartAfterBuy(cartRequest: CartUpdateRequest) {
    return this.http.put<boolean>(`${this.cartUrl}/update-amount`, cartRequest);
  }

}
