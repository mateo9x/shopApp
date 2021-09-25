import { MessageService } from 'primeng/api';
import { CartService } from './../../cart/cart.service';
import { Item } from './items.model';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ItemsService } from './items.service';
import { ItemCompsService } from '../item-comps-service';
import * as moment from 'moment';

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
  cartForAnonymousUser: Item[] = [];

  constructor(private itemService: ItemsService, private router: Router, private itemCompsService: ItemCompsService, private cartService: CartService,
    private messageService: MessageService) { }

  ngOnInit() {
    this.cols = [
      { field: 'brand', header: 'Marka' },
      { field: 'model', header: 'Nazwa' },
      { field: 'price', header: 'Cena' },
      { field: 'createDate', header: 'Data wystawienia' },
      { field: 'sellerName', header: 'Sprzedawca' },
      { field: 'cartAdd', header: '' }
    ];
    this.itemCompsService.getNavChangeEmitter().subscribe((response) => {
      this.itemService.findAllItemsByCategory(response).subscribe((respond) => {
        if (respond.length > 0) {
          respond.forEach((element) => {
            element.createDate = moment.utc(element.createDate).local().format('YYYY-MM-DD HH:mm');
          });
          this.items = respond;
          this.noData = false;
        } else {
          this.items = [];
          this.noData = true;
        }
      });
    });

  }

  addToCart(item: Item) {
    if (sessionStorage.getItem('id_token') !== null) {
      this.cartService.addItemToCartForUser(item.id).subscribe((response) => {
        if (response !== null) {
          this.messageService.add({ key: 'success', severity: 'success', summary: 'Dodano produkt do koszyka' });
        } else {
          this.messageService.add({ key: 'error', severity: 'error', summary: 'Produkt znajduje się już obecnie w Twoim koszyku' });
        }
      });
    } else {
      if (sessionStorage.getItem('cart') !== null) {
        this.cartForAnonymousUser = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
        if (this.cartForAnonymousUser.find((element) => element.id === item.id)) {
          this.messageService.add({ key: 'error', severity: 'error', summary: 'Produkt znajduje się już obecnie w Twoim koszyku' });
        } else {
          this.cartForAnonymousUser.push(item);
          sessionStorage.setItem('cart', JSON.stringify(this.cartForAnonymousUser));
          this.messageService.add({ key: 'success', severity: 'success', summary: 'Dodano produkt do koszyka' });
        }
      } else {
        this.cartForAnonymousUser.push(item);
        sessionStorage.setItem('cart', JSON.stringify(this.cartForAnonymousUser));
        this.messageService.add({ key: 'success', severity: 'success', summary: 'Dodano produkt do koszyka' });
      }
    }
  }

  openItemDetail(item: any) {
    // console.log('przejdz do detailsow', item);
    // this.router.navigate(['item-details']);
  }

}
