import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ItemCategory } from './item-category.model';
import { ItemCategoryService } from './item-category.service';

@Component({
  selector: 'item-category',
  templateUrl: './item-category.component.html',
  styleUrls: ['./item-category.component.scss']
})
export class ItemCategoryComponent implements OnInit {

  cols: any[];
  loading: boolean;
  items: ItemCategory[] = [];
  childItems: ItemCategory[] = [];
  itemParent: ItemCategory;
  itemChild: ItemCategory;
  id: number;

  constructor(private itemService: ItemCategoryService, private router: Router) { }

  ngOnInit() {
    this.itemService.findAllItemCategories().subscribe((response) => {
      this.items = response;
    });

  }

  showChilds(parent: ItemCategory) {
    this.childItems = [];
    this.items.forEach((child) => {
      if (child.itemCategoryParentId === parent.id) {
        this.childItems.push(child);
      }
    });
  }

  openCategoryComponent(child: ItemCategory) {
    this.router.navigate(['items', child.id]);
  }

}
