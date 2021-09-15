import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AppComponent } from 'src/app/app.component';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';
import { LocalStorageService } from '../../authenthication/local-storage.service';

@Component({
  selector: 'sign-in-user',
  templateUrl: './sign-in-user.component.html',
  styleUrls: ['./sign-in-user.component.scss']
})
export class SignInUserComponent implements OnInit {

  users: User[];
  cols: any[];
  loading: boolean;
  username: string;
  password: string;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService, @Inject(AppComponent) private appComponent: AppComponent,
    private localStorageService: LocalStorageService) { }

  ngOnInit() { }

  onSignInButton() {
    if (this.username !== undefined && this.password !== undefined) {
      const userObj = {
        username: this.username,
        password: this.password
      }

      if (this.localStorageService.get('id_token') !== null) {
        this.messageService.add({ key: 'error', severity: 'error', summary: 'Jesteś już zalogowany!' });
      } else {
        this.userService.signinUser(userObj).subscribe((response) => {
          this.appComponent.isUserLogged = true;
          this.localStorageService.set('id_token', response.token);
          this.router.navigate(['']);
        }, (error) => {
          this.messageService.add({ key: 'error', severity: 'error', summary: 'Nie poprawne dane logowania!' });
        });
      }
    }
  }

  signUp() {
    this.router.navigate(['sign-up-user']);
  }

}
