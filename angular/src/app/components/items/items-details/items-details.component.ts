import { UserService } from 'src/app/components/user/user.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemsService } from '../items/items.service';
import { Item } from '../items.model';

@Component({
  selector: 'items-details',
  templateUrl: './items-details.component.html',
  styleUrls: ['./items-details.component.scss']
})
export class ItemsDetailsComponent implements OnInit {

  item: Item = new Item();
  cols: any[];
  noData = false;
  itemId: any;
  isUserLogged = false;

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService,
    private messageService: MessageService, private confirmationService: ConfirmationService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(paramsId => {
      this.itemId = paramsId.id;
    });
    this.itemService.findItem(this.itemId).subscribe((response) => {
      if (response.description === null || response.description === undefined) {
        response.description = 'Brak opisu';
      }
      this.item = response;
      console.log(response);
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

  showSellerItems() {
    this.router.navigate(['items/seller', this.item.sellerId]);
  }

  openItemDetail(item: Item) {
    // const ref = this.dialogService.open(ItemsDialogComponent, {
    //   header: 'Szczegóły produktu ' + item.brand + ' ' + item.model,
    //   data: { item: Object.assign({}, item) },
    //   width: '50%'
    // });
  }

}
