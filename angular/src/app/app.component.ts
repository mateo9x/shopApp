import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UserService } from './components/user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@HostListener('mouseover', ['$event'])
export class AppComponent implements OnInit {

  isUserLogged: boolean;
  isCartEmpty = false;
  tabWasClosed = false;

  constructor(private userService: UserService, private router: Router, private messageService: MessageService) { }

  ngOnInit() {
    if ( sessionStorage.getItem("id_token") !== null) {
      this.isUserLogged = true;
    } else {
      this.isUserLogged = false;
    }
  }

  logOut() {
    sessionStorage.removeItem('id_token');
    this.isUserLogged = false;
    this.messageService.add({ key: 'success', severity: 'success', summary: 'Wylogowano pomyślnie!' });
  }

  doBeforeUnload() {
    if (document.visibilityState === 'hidden') {
      this.tabWasClosed = true;
    }

    return false;
  }

  doUnload() {
    if (this.tabWasClosed) {
      console.log('dupa zamykam')
    }
  }

}
