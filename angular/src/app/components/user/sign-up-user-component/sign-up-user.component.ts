import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

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

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) { }

  ngOnInit() { }

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

  onClose(){
    this.router.navigate(['']);
  }

  register() {
      this.userService.saveUser(this.user).subscribe((response) => {
        if (response !== null) {
        this.messageService.add({key:'success', severity: 'success', summary: 'Utworzono użytkownika pomyślnie'});
        this.userService.newUserWelcomeMail(this.user).subscribe((response) => {
          console.log('Wyslano maila', response);
        });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Użytkownik z podanym adresem e-mail istnieje!'});
      }
     
        
      }, (error) => {
        this.messageService.add({ severity: 'error', summary: 'Użytkownik nie został utworzony'});
      });
  }

}
