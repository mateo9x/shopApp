import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from 'src/app/components/user/user.service';
import {ToastService} from "../../toasts/toast.service";

@Component({
  selector: 'reset-password',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.scss']
})
export class ResetPasswordComponent {

  mail: string;
  doesUserExistsInDatabase = false;

  constructor(private userService: UserService, private router: Router, private toastService: ToastService) {
  }

  resetButton() {
    this.userService.doesUserWithEmailExists(this.mail).subscribe((response) => {
      this.doesUserExistsInDatabase = response;
    });
    if (this.doesUserExistsInDatabase) {
      this.userService.resetPassword(this.mail).subscribe((response) => {
        this.toastService.createSuccessToast('Wysłano link do zresetowania hasła na podany adres e-mail');
        this.router.navigate(['sign-in-user']);
      });
    } else {
      this.toastService.createErrorToast('Taki użytkownik nie istnieje w bazie danych!');
    }
  }

}
