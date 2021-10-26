import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AppService } from './app.service';

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
  searchQuery: any;

  constructor(private router: Router, private messageService: MessageService, private appService: AppService) { }

  ngOnInit() {
    if (sessionStorage.getItem("id_token") !== null) {
      this.isUserLogged = true;
    } else {
      this.isUserLogged = false;
    }
  }

  searchItem() {
    this.router.navigate(['items/query', this.searchQuery]);
  }

  logOut() {
    sessionStorage.removeItem('id_token');
    this.isUserLogged = false;
    this.messageService.add({ key: 'success', severity: 'success', summary: 'Wylogowano pomyślnie!' });
  }

  showSqlVersion() {
    this.appService.getSqlVersion().subscribe((response) => {
      if (response !== null) {
        this.messageService.add({ key: 'warn', severity: 'warn', summary: 'Aktualna wersja bazy danych: ' + response.version });
      } else {
        this.messageService.add({ key: 'warn', severity: 'warn', summary: 'Brak wersji bazy danych' });
      }
    });

  }

  // doBeforeUnload() {
  //   if (document.visibilityState === 'hidden') {
  //     this.tabWasClosed = true;
  //   }

  //   return false;
  // }

  // doUnload() {
  //   if (this.tabWasClosed) {
  //     console.log('dupa zamykam')
  //   }
  // }

}
