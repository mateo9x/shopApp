import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ItemCategory } from './item-category.model';

@Injectable({
  providedIn: 'root'
})
export class ItemCategoryService {

  private itemCategoryUrl = 'http://localhost:8080/api/items-category';

  constructor(private http: HttpClient) { }

  public findAllItemCategories(): Observable<ItemCategory[]> {
    return this.http.get<ItemCategory[]>(`${this.itemCategoryUrl}`);
  }

  public findItemCategory(id: any) {
    return this.http.get<ItemCategory>(`${this.itemCategoryUrl}/`, id);
  }

  public deleteItemCategory(id: any) {
    return this.http.delete<ItemCategory>(`${this.itemCategoryUrl}/`, id);
  }

  public saveItemCategory(item: ItemCategory) {
    return this.http.post<ItemCategory>(`${this.itemCategoryUrl}`, item);
  }

  public updateItemCategory(item: ItemCategory) {
    return this.http.put<ItemCategory>(`${this.itemCategoryUrl}`, item);

  }

}
