import {UserService} from 'src/app/components/user/user.service';
import {ConfirmationService} from 'primeng/api';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';
import {Order} from '../../order/order.model';
import * as moment from "moment/moment";
import {ToastService} from "../../toasts/toast.service";
import {BuyProductRequest, BuyProductService} from "../buy.service";

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
  order: Order = new Order();
  productSold = false;

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService,
              private toastService: ToastService, private confirmationService: ConfirmationService, private route: ActivatedRoute,
              private buyProductService: BuyProductService) {
  }

  ngOnInit() {
    this.route.params.subscribe(paramsId => {
      this.itemId = paramsId.id;
    });
    this.itemService.findItem(this.itemId).subscribe((response) => {
      if (!response.description) {
        response.description = 'Brak opisu';
      }
      if (response.amountAvailable.length === 0) {
        this.productSold = true;
      }
      response.createDate = moment.utc(response.createDate).local().format('YYYY-MM-DD HH:mm');
      this.item = response;
    });
    this.userService.isUserLogged().subscribe((response) => {
      if (response !== null) {
        this.isUserLogged = true;
      }
    })
  }

  buyConfirm() {
    if (this.item.amountSelected) {
      this.confirmationService.confirm({
        message: 'Czy na pewno chcesz zakupić ten produkt?',
        accept: () => {
          this.buyProduct();
        }
      });
    } else {
      this.toastService.createErrorToast('Należy wybrać ilość produktów!');
    }
  }

  buyProduct() {
    this.buyProductService.buyProduct(this.prepareBuyProductRequest(this.item));
  }

  showSellerItems() {
    this.router.navigate(['items/seller', this.item.sellerId]);
  }

  formatCreateDate(createDate: string) {
    return moment.utc(createDate).local().format('YYYY-MM-DD HH:mm');
  }

  getItemFirstPhoto(photoUrl: string) {
    if (photoUrl && photoUrl.includes(';')) {
      return photoUrl.split(';')[0];
    } else {
      return photoUrl;
    }
  }

  prepareBuyProductRequest(item: Item): BuyProductRequest {
    return {
      itemId: item.id,
      itemAmountSelected: item.amountSelected,
      itemAmountAvailable: item.amountAvailable
    };
  }

}
