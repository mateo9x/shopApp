import { DialogService } from 'primeng/dynamicdialog';
import { MessageService } from 'primeng/api';
import { CartService } from './../../cart/cart.service';
import { Item } from '../items.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemsService } from '../items.service';
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

  constructor(private itemService: ItemsService, private router: Router, private cartService: CartService,
    private messageService: MessageService, private dialogService: DialogService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      if (params['id'] !== undefined) {
        this.loadData(1, params['id']);
      } else if (params['sellerId'] !== undefined) {
        this.loadData(2, params['sellerId']);
      } else if (params['query'] !== undefined) {
        this.loadData(3, params['query']);
      }
    });
    this.cols = [
      { field: 'brand', header: 'Marka' },
      { field: 'model', header: 'Nazwa' },
      { field: 'price', header: 'Cena' },
      { field: 'createDate', header: 'Data wystawienia' },
      { field: 'sellerName', header: 'Sprzedawca' },
      { field: 'cartAdd', header: '' }
    ];
  }

  loadData(option: number, param: any) {
    if (option === 1) {
      this.itemService.findAllItemsByCategory(param).subscribe((respond) => {
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
    } else if (option === 2) {
      this.itemService.findAllItemsBySellerId(param).subscribe((respond) => {
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
    } else if (option === 3) {
      this.itemService.findAllBySearchQuery(param).subscribe((respond) => {
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
    }
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

  openItemDetail(item: Item) {
    this.router.navigate(['items-details', item.id]);
  }

}
