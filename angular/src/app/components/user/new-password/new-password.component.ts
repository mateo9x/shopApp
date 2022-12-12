import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {User} from 'src/app/components/user/user.model';
import {UserService} from 'src/app/components/user/user.service';
import {ToastService} from "../../toasts/toast.service";

@Component({
  selector: 'new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.scss']
})
export class NewPasswordComponent implements OnInit {

  user: User = new User;
  token: string;

  constructor(private userService: UserService, private router: Router, private toastService: ToastService) {
  }

  ngOnInit() {
    this.token = this.router.routerState.snapshot.url;
    this.token = this.token.substring(14, this.token.length - 1);
  }

  savePassword() {
    this.user.resetToken = this.token;
    if (this.user.password) {
      this.userService.getByUserToken(this.user).subscribe((response) => {
        if (response !== null) {
          this.userService.updateUserPasswordByToken(this.user).subscribe((response) => {
            if (response) {
              this.toastService.createSuccessToast('Hasło zaaktualizowane pomyślnie');
              this.router.navigate(['']);
            } else {
              this.toastService.createErrorToast('Hasło nie może być takie same jak poprzednie');
            }
          });
        } else {
          this.toastService.createErrorToast('Token stracił swoją ważność');
        }
      });
    }
  }

}
