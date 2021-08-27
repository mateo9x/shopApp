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

  users: User[];
  cols: any[];
  loading: boolean;
  username: string;
  password: string;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {

  }

  onSignInButton() {
      const userObj = {
        username: this.username,
        password: this.password
      }
      console.log(userObj);
      this.userService.signinUser(userObj).subscribe((response) => {
        this.router.navigate(['']);
      });
  }

  signUp(){
    this.router.navigate(['sign-up-user']);
  }

}
