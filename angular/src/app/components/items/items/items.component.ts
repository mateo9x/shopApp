import { ItemCategoryComponent } from './../item-category/item-category.component';
import { Item } from './items.model';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ItemsService } from './items.service';
import { ItemCompsService } from '../item-comps-service';

@Component({
  selector: 'items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.scss']
})
export class ItemsComponent implements OnInit {

  items: Item[] = [];
  cols: any[];
  noData = false;
  selectedItem: Item;

  constructor(private itemService: ItemsService, private router: Router, private itemCompsService: ItemCompsService) { }

  ngOnInit() {
    this.cols = [
      { field: 'brand', header: 'Marka' },
      { field: 'model', header: 'Nazwa' },
      { field: 'price', header: 'Cena' },
      { field: 'cartAdd', header: '' }
    ];
    this.itemCompsService.getNavChangeEmitter().subscribe((response) => {
      this.itemService.findAllItemsByCategory(response).subscribe((respond) => {
        if(respond.length > 0) {
        this.items = respond;
        this.noData = false;
        } else {
          this.items = [];
          this.noData = true;
        }
      });
    });

  }

  addToCart(item: any) {
    console.log(item);
  }

}
