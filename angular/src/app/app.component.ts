import {Component, HostListener, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {MessageService} from 'primeng/api';
import {ItemsService} from "./components/items/items.service";
import {ItemCategoryService} from "./components/items/item-category/item-category.service";
import {ItemCategory} from "./components/items/item-category/item-category.model";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@HostListener('mouseover', ['$event'])
export class AppComponent implements OnInit {

  isUserLogged: boolean;
  isCartEmpty = false;
  searchQuery: any;
  itemCategories: ItemCategory[] = [];

  constructor(private router: Router, private messageService: MessageService, private itemCategoryService: ItemCategoryService) {
  }

  ngOnInit() {
    this.isUserLogged = sessionStorage.getItem("id_token") !== null;
    this.loadItemCategories();
  }

  searchItem() {
    this.router.navigate(['items/query', this.searchQuery]);
  }

  logOut() {
    sessionStorage.removeItem('id_token');
    this.isUserLogged = false;
    this.messageService.add({key: 'success', severity: 'success', summary: 'Wylogowano pomyślnie!'});
  }

  openNav() {
    const mySideNav = document.getElementById("mySidenav");
    if (mySideNav) {
      mySideNav.style.width = "250px";
    }
  }

  closeNav() {
    const mySideNav = document.getElementById("mySidenav");
    if (mySideNav) {
      mySideNav.style.width = "0";
    }
  }

  loadItemCategories() {
    this.itemCategoryService.findAllItemCategories().subscribe((response) => {
      this.itemCategories = response;
    });
  }

  displayItemsFromCategory(itemCategoryId: number) {
    this.closeNav();
    this.router.navigate(['items/', itemCategoryId]);
  }
}
