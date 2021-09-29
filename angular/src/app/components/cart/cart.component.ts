import { ItemsService } from './../items/items/items.service';
import { Item } from '../items/items.model';
import { UserService } from './../user/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { CartService } from './cart.service';
import * as moment from 'moment';

@Component({
  selector: 'cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cols: any[];
  selectedCartItem: Item;
  cartItems: Item[] = [];
  noData = true;
  userLogged = false;
  userId: number;
  constructor(private cartService: CartService, private router: Router, private messageService: MessageService, private userService: UserService, private itemService: ItemsService) { }

  ngOnInit() {
    this.userService.isUserLogged().subscribe((response) => {
      if (response !== null) {
        this.userLogged = true;
        this.userId = response.id;
      }
      this.loadData();
    });

    this.cols = [
      { field: 'brand', header: 'Marka' },
      { field: 'model', header: 'Nazwa' },
      { field: 'price', header: 'Cena' },
      { field: 'createDate', header: 'Data wystawienia' },
      { field: 'sellerName', header: 'Sprzedawca' },
      { field: 'deleteFromCart', header: '' }
    ];
  }

  loadData() {
    if (this.userLogged === true) {
      this.itemService.findCartForUser(this.userId).subscribe((response) => {
        if (response.length > 0) {
          response.forEach((element) => {
            element.createDate = moment.utc(element.createDate).local().format('YYYY-MM-DD HH:mm');
          });
          this.cartItems = response;
          this.noData = false;
        } else {
          this.noData = true;
        }
      });
    } else {
      if (sessionStorage.getItem('cart') !== null) {
        this.cartItems = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
        this.cartItems.forEach((element) => {
          element.createDate = moment.utc(element.createDate).local().format('YYYY-MM-DD HH:mm');
        });
        this.noData = false;
      } else {
        this.noData = true;
      }
    }
  }


  deleteFromCart(item: Item) {
    console.log(item);
    if (this.userLogged === true) {
      this.cartService.deleteItemFromCart(item.id).subscribe((response) => {
        this.messageService.add({ key: 'success', severity: 'success', summary: 'Produkt usunięty z koszyka' });
        this.ngOnInit();
      });
    } else {
      this.cartItems = this.cartItems.filter((element) => element.id !== item.id);
      sessionStorage.setItem('cart', JSON.stringify(this.cartItems));
      this.messageService.add({ key: 'success', severity: 'success', summary: 'Produkt usunięty z koszyka' });
      this.ngOnInit();
    }
  }

  openItemDetail(item: Item) {
    this.router.navigate(['items-details', item.id]);
  }

}
