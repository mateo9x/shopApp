import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private baseUrl = 'http://localhost:8080/api/sql-version';
  constructor(private http: HttpClient) {
    http.head(this.baseUrl, { responseType: 'text' });
   }

  public getSqlVersion(): Observable<any> {
    return this.http.get<String>(`${this.baseUrl}`);
  }

}
