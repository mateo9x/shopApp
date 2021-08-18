import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  public findAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }

  public findUser(id: any) {
    return this.http.get<User>(`${this.baseUrl}/`, id);
  }

  public deleteUser(id: any) {
    return this.http.delete<User>(`${this.baseUrl}/`, id);
  }

  public saveUser(user: User) {
    return this.http.post<User>(`${this.baseUrl}`, user);
  }

  public updateUser(user: User) {
    return this.http.put<User>(`${this.baseUrl}`, user);
  }
}
