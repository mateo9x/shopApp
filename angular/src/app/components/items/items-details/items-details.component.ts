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
  cartItems: Item[] = [];
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
    this.confirmationService.confirm({
      message: 'Czy na pewno chcesz zakupić ten produkt?',
      accept: () => {
        this.buyProduct();
      }
    });
  }

  buyProduct() {
    this.order.date = new Date();
    this.order.itemId = this.item.id;
    this.orderService.createOrder(this.order).subscribe((response) => {
      if (response !== null) {
        this.cartService.deleteItemFromAllCarts(this.itemId).subscribe((response) => {

        });
        if (sessionStorage.getItem('cart') !== null) {
          this.cartItems = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
          this.cartItems.forEach((element, index) => {
            if (element.id === this.item.id) {
              delete this.cartItems[index];
            }
          });
          sessionStorage.setItem('cart', JSON.stringify(this.cartItems));
        }
        this.toastService.createSuccessToast('Produkt został zakupiony');
        this.router.navigate(['order-process', response.id]);
      } else {
        this.toastService.createErrorToast('Musisz być zalogowany, żeby kupować produkty');
      }
    });
  }

  showSellerItems() {
    this.router.navigate(['items/seller', this.item.sellerId]);
  }

}
