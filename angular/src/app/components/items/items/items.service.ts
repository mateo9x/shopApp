import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from './items.model';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {

  private itemsUrl = 'http://localhost:8080/api/items-category';

  constructor(private http: HttpClient) { }

  public findAllItemCategories(): Observable<Item[]> {
    return this.http.get<Item[]>(`${this.itemsUrl}`);
  }

  public findItemCategory(id: any) {
    return this.http.get<Item>(`${this.itemsUrl}/`, id);
  }

  public deleteItemCategory(id: any) {
    return this.http.delete<Item>(`${this.itemsUrl}/`, id);
  }

  public saveItemCategory(item: Item) {
    return this.http.post<Item>(`${this.itemsUrl}`, item);
  }

  public updateItemCategory(item: Item) {
    return this.http.put<Item>(`${this.itemsUrl}`, item);

  }

}
