import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from './items.model';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {

  private itemsUrl = 'http://localhost:8080/api/item';

  constructor(private http: HttpClient) {
  }

  public findAllItems(): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.itemsUrl}`);
  }

  public findItem(id: any) {
    return this.http.get<Item>(`${this.itemsUrl}/${id}`, id);
  }

  public deleteItem(id: any) {
    return this.http.delete<Item>(`${this.itemsUrl}/`, id);
  }

  public saveItem(item: Item) {
    return this.http.post<Item>(`${this.itemsUrl}`, item);
  }

  public updateItem(item: Item) {
    return this.http.put<Item>(`${this.itemsUrl}`, item);

  }

  public findCartForUser(id: number): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.itemsUrl}/cart`);
  }

  public findAllItemsByCategory(id: any): Observable<Item[]> {
    return this.http.post<Item[]>(`${this.itemsUrl}/category/${id}`, id);

  }

}
