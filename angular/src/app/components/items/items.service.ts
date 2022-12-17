import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Item} from './items.model';

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

  public saveItem(item: Item, photos: any[]) {
    var fd = new FormData();
    fd.append('item', JSON.stringify(item));
    photos.forEach((photo) => {
      fd.append('photos', photo);
    });
    return this.http.post<Item>(`${this.itemsUrl}`, fd);
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

  public doesItemAlreadyExists(item: Item): Observable<any> {
    let searchParams = new HttpParams()
      .set('brand', item.brand)
      .set('model', item.model)
      .set('price', item.price)
      .set('itemCategoryId', item.itemCategoryId)
      .set('sellerId', item.sellerId);
    return this.http.get<Boolean>(`${this.itemsUrl}/exists?` + searchParams.toString());
  }

}
