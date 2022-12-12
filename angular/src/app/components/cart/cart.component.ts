import {Item} from '../items/items.model';
import {UserService} from '../user/user.service';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CartService} from './cart.service';
import {Cart} from "./cart.model";
import {ToastService} from "../toasts/toast.service";
import {ConfirmationService} from "primeng/api";

@Component({
  selector: 'cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cartItems: Cart[] = [];
  noData = true;
  userLogged = false;
  userId: number;

  constructor(private cartService: CartService, private router: Router, private toastService: ToastService, private userService: UserService,
              private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.userService.isUserLogged().subscribe((response) => {
      if (response !== null) {
        this.userLogged = true;
        this.userId = response.id;
      }
      this.loadData();
    });
  }

  loadData() {
    if (this.userLogged) {
      this.cartService.findCartForUserLogged().subscribe((response) => {
        this.cartItems = response;
      });
    } else {
      if (sessionStorage.getItem('cart') !== null) {
        this.cartItems = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
        // this.cartItems.forEach((element) => {
        //   element.createDate = moment.utc(element.createDate).local().format('YYYY-MM-DD HH:mm');
        // });
        this.noData = false;
      } else {
        this.noData = true;
      }
    }
  }

  deleteFromCartConfirmDialog(cart: Cart) {
    this.confirmationService.confirm({
      message: 'Usunięcie produktu z koszyka powoduje usunięcie wszystkich sztuk w koszyku danego produktu. Czy usunąc produkt z koszyka?',
      accept: () => {
        this.deleteFromCart(cart);
      }
    });
  }

  deleteFromCart(cart: Cart) {
    if (this.userLogged) {
      this.cartService.deleteItemFromCart(cart.itemId).subscribe((response) => {
        this.toastService.createSuccessToast('Produkt usunięty z koszyka');
        this.ngOnInit();
      });
    } else {
      this.cartItems = this.cartItems.filter((element) => element.itemId !== cart.itemId);
      sessionStorage.setItem('cart', JSON.stringify(this.cartItems));
      this.toastService.createSuccessToast('Produkt usunięty z koszyka');
      this.ngOnInit();
    }
  }

  openItemDetail(itemId: number) {
    this.router.navigate(['items-details', itemId]);
  }

  buyProduct(cart: Cart) {

  }

  getItemFirstPhoto(photoUrl: string) {
    if (photoUrl.includes(';')) {
      return photoUrl.split(';')[0];
    } else {
      return photoUrl;
    }
  }

}
