import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

@Component({
  selector: 'profile-data',
  templateUrl: './profile-data.component.html',
  styleUrls: ['./profile-data.component.scss']
})
export class ProfileDataComponent implements OnInit {

  user: User = new User();
  cols: any[];
  loading: boolean;
  changePswdForm = false;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) { }

  ngOnInit() {
    this.userService.getUserLogged().subscribe((response) => {
      this.user = response;
    });

  }

  newPassword() {
    this.user.password = '';
    this.changePswdForm = true;
  }

  changePassword() {
    this.userService.updateUserPassword(this.user).subscribe((response) => {
      if (response === true) {
      this.messageService.add({ key: 'success', severity: 'success', summary: 'Hasło zaaktualizowane pomyślnie' });
      this.router.navigate(['']);
      } else {
        this.messageService.add({ key: 'error', severity: 'error', summary: 'Hasło nie może być takie same jak poprzednie' });
      }
    });
  }



}
