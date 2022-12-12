import {Component, OnInit} from '@angular/core';
import {User} from 'src/app/components/user/user.model';
import {UserService} from 'src/app/components/user/user.service';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User = new User;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getUserLogged().subscribe((response) => {
      this.user = response;
    });

  }

}
