import { MessageService } from 'primeng/api';
import { Component, OnInit } from '@angular/core';
import { Router, RouterStateSnapshot } from '@angular/router';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

@Component({
  selector: 'new-password',
  templateUrl: './new-password.component.html',
  styleUrls: ['./new-password.component.scss']
})
export class NewPasswordComponent implements OnInit {

  user: User = new User;
  token: string;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) { }

  ngOnInit() {
    this.token = this.router.routerState.snapshot.url;
    this.token = this.token.substring(14, this.token.length - 1);
  }

  savePassword() {
    this.user.resetToken = this.token;
    if (this.user.password !== null || this.user.password !== undefined) {
      this.userService.getByUserToken(this.user).subscribe((response) => {
        if (response !== null) {
          this.userService.updateUserPasswordByToken(this.user).subscribe((response) => {
            if (response === true) {
              this.messageService.add({ key: 'success', severity: 'success', summary: 'Hasło zaaktualizowane pomyślnie' });
              this.router.navigate(['']);
            } else {
              this.messageService.add({ key: 'error', severity: 'error', summary: 'Hasło nie może być takie same jak poprzednie' });
            }
          });
        } else {
          this.messageService.add({ key: 'error', severity: 'error', summary: 'Token stracił swoją ważność' });
        }
      });
    }
  }

}
