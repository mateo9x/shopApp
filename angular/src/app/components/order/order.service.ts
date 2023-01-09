import { OrderAddress } from './order.address.model';
import { OrderPayment } from './order.payment.model';
import { Order } from './order.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {APP_BASE_URL} from "../../app.service";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private ordersUrl = APP_BASE_URL + '/api/orders';
  private ordersAddressUrl = APP_BASE_URL + '/api/orders-address';
  private ordersPaymentUrl = APP_BASE_URL + '/api/orders-payment';


  constructor(private http: HttpClient) {
  }

  public findAllOrderPayments(): Observable<OrderPayment[]> {
    return this.http.get<OrderPayment[]>(`${this.ordersPaymentUrl}`);
  }

  public returnProduct(id: number, amountOfProductsToReturn: number) {
    return this.http.delete<Order>(`${this.ordersUrl}/return?id=${id}&amountOfProductsToReturn=${amountOfProductsToReturn}`);
  }

  public createOrder(order: Order) {
    return this.http.post<Order>(`${this.ordersUrl}`, order);
  }

  public finishOrderProcess(order: Order) {
    return this.http.put<Order>(`${this.ordersUrl}`, order);

  }

  public findOrdersForUser(id: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.ordersUrl}/user/${id}`);
  }

  public findOrderById(id: any): Observable<Order> {
    return this.http.get<Order>(`${this.ordersUrl}/${id}`);
  }

  public saveOrderAddress(orderAdd: OrderAddress) {
    return this.http.post<OrderAddress>(`${this.ordersAddressUrl}`, orderAdd);
  }

  public findOrdersForSeller(sellerId: number): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.ordersUrl}/seller/${sellerId}`);
  }

}
