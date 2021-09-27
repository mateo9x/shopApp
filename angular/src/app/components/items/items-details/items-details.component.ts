import { UserService } from 'src/app/components/user/user.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CartService } from '../../cart/cart.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ItemCompsService } from '../item-comps-service';
import * as moment from 'moment';
import { DialogService } from 'primeng/dynamicdialog';
import { ItemsService } from '../items/items.service';
import { Item } from '../items.model';

@Component({
  selector: 'items-details',
  templateUrl: './items-details.component.html',
  styleUrls: ['./items-details.component.scss']
})
export class ItemsDetailsComponent implements OnInit {

  item: Item;
  cols: any[];
  noData = false;
  itemId: number;
  isUserLogged = false;

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService,
    private messageService: MessageService, private confirmationService: ConfirmationService) { }

  ngOnInit() {
    this.itemId = this.router.routerState.snapshot.url.substring(15, this.router.routerState.snapshot.url.length) as unknown as number;
    this.itemService.findItem(this.itemId).subscribe((response) => {
      if (response.description === null || response.description === undefined) {
        response.description = 'Brak opisu';
      }
      this.item = response;
    });
    this.userService.isUserLogged().subscribe((response) => {
      if (response !== null) {
        this.isUserLogged = true;
      }
    })
  }

  buyConfirm() {
    this.confirmationService.confirm({
      message: 'Czy na pewno chcesz zakupić ten produkt?',
      accept: () => {
        this.buyProduct();
      }
    });
  }

  buyProduct() {

    console.log('kupujemy');
  }

  openItemDetail(item: Item) {
    // const ref = this.dialogService.open(ItemsDialogComponent, {
    //   header: 'Szczegóły produktu ' + item.brand + ' ' + item.model,
    //   data: { item: Object.assign({}, item) },
    //   width: '50%'
    // });
  }

}
