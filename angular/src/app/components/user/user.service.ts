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

  public updateUserPassword(user: User) {
    return this.http.put<boolean>(`${this.baseUrl}/password`, user);
  }

  public updateUserPasswordByToken(user: User) {
    return this.http.put<boolean>(`${this.baseUrl}/password/token`, user);
  }

  public signinUser(user: any) {
    return this.http.post<any>(`${this.authenthicationUrl}/login`, user);
  }

  public logoutUser(request: any, response: any) {
    return this.http.request<any>(`${this.authenthicationUrl}/logout`, request, response);
  }

  public isUserLogged() {
    return this.http.get<User>(`${this.authenthicationUrl}/is-user-logged`);
  }

  public getUserLogged(): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/logged`);
  }

  public newUserWelcomeMail(user: User) {
    return this.http.post<User>(`${this.mailSenderUrl}/new-user-welcome-email`, user);
  }

  public resetPassword(user: User) {
    return this.http.post<User>(`${this.baseUrl}/resetPassword`, user);
  }


  public getByUserToken(user: User) {
    return this.http.post<User>(`${this.baseUrl}/token-user`, user);
  }

}
