import {CartService} from '../../cart/cart.service';
import {UserService} from 'src/app/components/user/user.service';
import {ConfirmationService} from 'primeng/api';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';
import {OrderService} from '../../order/order.service';
import {Order} from '../../order/order.model';
import * as moment from "moment/moment";
import {ToastService} from "../../toasts/toast.service";
import {Cart, CartUpdateRequest} from "../../cart/cart.model";

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
  cartItems: Cart[] = [];
  productSold = false;

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService,
              private toastService: ToastService, private confirmationService: ConfirmationService, private route: ActivatedRoute,
              private orderService: OrderService, private cartService: CartService) {
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
    const amountAvailableAfterBuy = this.item.amountAvailable - this.item.amountSelected;
    this.order.date = new Date();
    this.order.itemId = this.item.id;
    this.order.amountBought = this.item.amountSelected;
    this.orderService.createOrder(this.order).subscribe((response) => {
      if (response !== null) {
        this.cartService.updateItemAmountCartAfterBuy(this.prepareRequestModel(amountAvailableAfterBuy)).subscribe((itemAmountUpdated) => {
          if (sessionStorage.getItem('cart') !== null) {
            this.cartItems = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
            this.cartItems.forEach((element, index) => {
              if (element.itemId === this.item.id) {
                if (amountAvailableAfterBuy === 0) {
                  delete this.cartItems[index];
                } else if (amountAvailableAfterBuy > 0 && this.cartItems[index].amountSelected > amountAvailableAfterBuy) {
                  this.cartItems[index].amountSelected = amountAvailableAfterBuy;
                }
              }
            });
            sessionStorage.setItem('cart', JSON.stringify(this.cartItems));
          }
          this.toastService.createSuccessToast('Produkt został zakupiony');
          this.router.navigate(['order-process', response.id]);
        });
      } else {
        this.toastService.createErrorToast('Musisz być zalogowany, żeby kupować produkty');
      }
    });
  }

  showSellerItems() {
    this.router.navigate(['items/seller', this.item.sellerId]);
  }

  formatCreateDate(createDate: string) {
    return moment.utc(createDate).local().format('YYYY-MM-DD HH:mm');
  }

  prepareRequestModel(amountAvailableAfterBuy: number): CartUpdateRequest {
    return new CartUpdateRequest(this.item.id, amountAvailableAfterBuy);
  }

  getItemFirstPhoto(photoUrl: string) {
    if (photoUrl && photoUrl.includes(';')) {
      return photoUrl.split(';')[0];
    } else {
      return photoUrl;
    }
  }

}
