import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User = new User();
  cols: any[];
  loading: boolean;
  changePswdForm = false;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) { }

  ngOnInit() {
    this.userService.getUserLogged().subscribe((response) => {
      this.user =response;
    });

  }

  changePassword() {
    this.user.password = '';
    this.changePswdForm = true;
  }
  onClose(){
    this.router.navigate(['']);
  }

  

}
