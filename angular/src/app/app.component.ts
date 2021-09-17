import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { ItemCategory } from './components/items/item-category/item-category.model';
import { ItemCategoryService } from './components/items/item-category/item-category.service';
import { UserService } from './components/user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
@HostListener('mouseover', ['$event'])
export class AppComponent {

  isUserLogged: boolean;
  isCartEmpty = false;
  selectedItemCategory: ItemCategory;
  itemCategories: ItemCategory[] = [];

  constructor(private userService: UserService, private router: Router, private itemCategoryService: ItemCategoryService, private messageService: MessageService) { }

  ngOnInit() {
    this.itemCategoryService.findAllItemCategories().subscribe((response) => {
      response.forEach((element) => {
        if (element.itemCategoryParentId !== null) {
          this.itemCategories.push(element);
        }
      });
    });
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

}
