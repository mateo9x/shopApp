import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ItemCategory } from './components/item-category/item-category.model';
import { ItemCategoryService } from './components/item-category/item-category.service';
import { UserService } from './components/user/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  isUserLogged = false;
  isCartEmpty = false;
  selectedItemCategory: ItemCategory;
  itemCategories: ItemCategory[] = [];
  
  constructor(private userService: UserService, private router: Router, private itemCategoryService: ItemCategoryService) { }

  ngOnInit(){
    this.userService.isUserLogged().subscribe((response) => {
      if (response !== false) {
      this.isUserLogged = true;
      }
    });
    this.itemCategoryService.findAllItemCategories().subscribe((response) => {
      response.forEach((element) => {
        if (element.itemCategoryParentId !== null) {
          this.itemCategories.push(element);
        }
      });
    });
  }

  logOut(){
    this.userService.logoutUser().subscribe((response) => {
      console.log(response);
    this.isUserLogged = false;
    });
  }
  
}
