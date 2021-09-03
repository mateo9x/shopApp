import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/users';
  private authenthicationUrl = 'http://localhost:8080';
  private mailSenderUrl = 'http://localhost:8080/api/mail';

  constructor(private http: HttpClient) { }

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

  public signinUser(user: any) {
    return this.http.post<User>(`${this.authenthicationUrl}/login`, user);
  }

  public logoutUser() {
    return this.http.get<any>(`${this.authenthicationUrl}/logout`);
  }

  public isUserLogged() {
    return this.http.get<boolean>(`${this.authenthicationUrl}/is-user-logged`);
  }

  public newUserWelcomeMail(user: User) {
    return this.http.post<User>(`${this.mailSenderUrl}/new-user-welcome-email`, user);
  }
  
}
