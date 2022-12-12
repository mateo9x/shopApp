import {ItemCategoryService} from '../item-category/item-category.service';
import {ItemCategory} from '../item-category/item-category.model';
import {UserService} from 'src/app/components/user/user.service';
import {ConfirmationService} from 'primeng/api';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';

@Component({
  selector: 'sell-items',
  templateUrl: './sell-items.component.html',
  styleUrls: ['./sell-items.component.scss']
})
export class SellItemsComponent implements OnInit {

  item: Item = new Item();
  selectedCategoryParent: ItemCategory;
  selectedCategoryChild: ItemCategory;
  parentCategories: ItemCategory[] = [];
  childCategories: ItemCategory[] = [];
  categories: ItemCategory[] = [];

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService, private confirmationService: ConfirmationService, private itemCategoryService: ItemCategoryService) {
  }

  ngOnInit() {
    this.itemCategoryService.findAllItemCategories().subscribe((response) => {
      this.categories = response;
      this.parentCategories = this.categories.filter((element) => element.itemCategoryParentId === null);
    });
  }

  findChildCategory(parentCategory: ItemCategory) {
    this.childCategories = this.categories.filter((element) => element.itemCategoryParentId === parentCategory.id);
  }

  addProductConfirm() {
    this.confirmationService.confirm({
      message: 'Czy na pewno chcesz wystawić ten produkt na sprzedaż?',
      accept: () => {
        this.addProduct();
      }
    });
  }

  addProduct() {

  }

  showSellerItems() {
    this.router.navigate(['items/seller', this.item.sellerId]);
  }

}
