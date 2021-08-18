import { Component, OnInit } from '@angular/core';
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

  constructor(private userService: UserService) { }

  ngOnInit() {
  }



  register() {
    if (this.user.id === null || this.user.id === undefined) {
      this.userService.saveUser(this.user).subscribe((response) => {
        console.log(response);
      }, (error) => {
        console.log(error);
      });
      this.user = new User();
    }
  }

}
