import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
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

  constructor(private itemService: ItemCategoryService, private router: Router, private messageService: MessageService) { }

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
    console.log(child);
  }

}
