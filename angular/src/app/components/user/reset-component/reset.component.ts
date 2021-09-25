import { MessageService } from 'primeng/api';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

@Component({
  selector: 'reset-password',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  user: User = new User;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) { }

  ngOnInit() { }

  resetButton() {
    if (this.user.mail !== undefined && this.user.mail !== undefined) {
      this.userService.resetPassword(this.user).subscribe((response) => {
        this.messageService.add({ key: 'success', severity: 'success', summary: 'Wysłano link do zresetowania hasła na podany adres e-mail'});
        this.router.navigate(['sign-in-user']);
      });
    }
  }

}
