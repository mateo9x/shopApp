import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {User} from 'src/app/components/user/user.model';
import {UserService} from 'src/app/components/user/user.service';
import {ToastService} from "../../toasts/toast.service";

@Component({
  selector: 'sign-up-user',
  templateUrl: './sign-up-user.component.html',
  styleUrls: ['./sign-up-user.component.scss']
})
export class SignUpUserComponent implements OnInit {

  user: User = new User();
  cols: any[];
  loading: boolean;
  users: User[];

  constructor(private userService: UserService, private router: Router, private toastService: ToastService) {
  }

  ngOnInit() {
  }

  clear() {
    this.user.username = '';
    this.user.firstName = '';
    this.user.lastName = '';
    this.user.password = '';
    this.user.password2 = '';
    this.user.mail = '';
    this.user.street = '';
    this.user.streetNumber = undefined;
    this.user.city = '';
  }

  register() {
    this.userService.saveUser(this.user).subscribe((response) => {
      if (response !== null) {
        this.toastService.createSuccessToast('Utworzono użytkownika pomyślnie');
        this.userService.newUserWelcomeMail(this.user).subscribe((response) => {
          console.log('Wyslano maila', response);
        });
        this.router.navigate(['']);
      } else {
        this.toastService.createErrorToast('Użytkownik o takim loginie/e-mail-u już istnieje!');
      }

    }, (error) => {
      this.toastService.createErrorToast('Użytkownik nie został utworzony');
    });
  }

  lowerCaseLogin(user: User) {
    if (user && user.username) {
      user.username = user.username.toLowerCase();
    }
  }

}
