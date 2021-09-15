import { HttpRequest, HttpResponse } from '@angular/common/http';
import { Component, HostListener, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { interval, Observable } from 'rxjs';
import { takeWhile } from 'rxjs/operators';
import { LocalStorageService } from './components/authenthication/local-storage.service';
import { ItemCategory } from './components/item-category/item-category.model';
import { ItemCategoryService } from './components/item-category/item-category.service';
import { SignInUserComponent } from './components/user/sign-in-user-component/sign-in-user.component';
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
  
  constructor(private userService: UserService, private router: Router, private itemCategoryService: ItemCategoryService, private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.itemCategoryService.findAllItemCategories().subscribe((response) => {
      response.forEach((element) => {
        if (element.itemCategoryParentId !== null) {
          this.itemCategories.push(element);
        }
      });
    });
    if (this.localStorageService.get("id_token") !== null) {
      this.isUserLogged = true;
    } else {
      this.isUserLogged = false;
    }
  }
  
  logOut() {
    this.localStorageService.remove('id_token');
    this.isUserLogged = false;
  }
  
}
