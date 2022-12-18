import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ItemCategory } from './item-category.model';
import {APP_BASE_URL} from "../../../app.service";

@Injectable({
  providedIn: 'root'
})
export class ItemCategoryService {

  private itemCategoryUrl = APP_BASE_URL + '/api/items-category';

  constructor(private http: HttpClient) { }

  public findAllItemCategories(): Observable<ItemCategory[]> {
    return this.http.get<ItemCategory[]>(`${this.itemCategoryUrl}`);
  }

  public findItemCategory(id: any): Observable<ItemCategory> {
    return this.http.get<ItemCategory>(`${this.itemCategoryUrl}/${id}`);
  }

}
