import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

@Component({
  selector: 'sign-in-user',
  templateUrl: './sign-in-user.component.html',
  styleUrls: ['./sign-in-user.component.scss']
})
export class SignInUserComponent implements OnInit {

  users: Observable<User[]>;
  cols: any[];
  loading: boolean;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.users = this.userService.findAllUsers();
  }

  signUp(){
    this.router.navigate(['sign-up-user']);
  }

}
