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

  public findItem(id: any): Observable<any> {
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

  public findAllItemsByCategory(id: any): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.itemsUrl}/category/${id}`);

  }

  public findAllItemsBySellerId(id: any): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.itemsUrl}/seller/${id}`);

  }

  public findAllBySearchQuery(query: any): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.itemsUrl}/query/${query}`);

  }

}
