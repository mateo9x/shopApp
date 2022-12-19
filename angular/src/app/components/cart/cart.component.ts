import {UserService} from '../user/user.service';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {CartService} from './cart.service';
import {Cart} from "./cart.model";
import {ToastService} from "../toasts/toast.service";
import {ConfirmationService} from "primeng/api";
import {BuyProductRequest, BuyProductService} from "../items/buy.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cartItems: Cart[] = [];
  userLogged = false;
  userId: number;
  sortOptions = [{name: 'Cena rosnąco', value: 'price_asc'},
    {name: 'Cena malejąco', value: 'price_desc'}];
  selectedSortOption: any;

  constructor(private cartService: CartService, private router: Router, private toastService: ToastService, private userService: UserService,
              private confirmationService: ConfirmationService, private buyProductService: BuyProductService, private sanitizer: DomSanitizer) {
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
      this.cartService.findCartItemsForUserLogged().subscribe((response) => {
        this.cartItems = response;
      });
    } else {
      if (sessionStorage.getItem('cart') !== null) {
        this.cartItems = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
        this.cartItems = this.cartItems.filter(cartItem => cartItem);
        sessionStorage.setItem('cart', JSON.stringify(this.cartItems));
      }
    }
  }

  deleteFromCartConfirmDialog(cart: Cart) {
    const productFullName = cart.itemBrand + ' ' + cart.itemModel;
    this.confirmationService.confirm({
      acceptLabel: 'TAK',
      rejectLabel: 'NIE',
      header: 'Potwierdź usunięcie produktu z koszyka',
      message: 'Usunięcie produktu ' + productFullName + ' z koszyka powoduje usunięcie wszystkich sztuk w koszyku danego produktu. Czy usunąc ' + cart.amountSelected + ' szt. produktu z koszyka?',
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

  buyProductConfirmDialog(cart: Cart) {
    const productFullName = cart.itemBrand + ' ' + cart.itemModel;
    this.confirmationService.confirm({
      acceptButtonStyleClass: 'red-button',
      acceptLabel: 'TAK',
      rejectLabel: 'NIE',
      header: 'Potwierdź zakup produktu',
      message: 'Czy na pewno chcesz zakupić: ' + productFullName + ' (' + cart.amountSelected + ' szt.) ?',
      accept: () => {
        this.buyProduct(cart);
      }
    });
  }

  buyProduct(cart: Cart) {
    this.buyProductService.buyProduct(this.prepareBuyProductRequest(cart));
  }

  getItemMainPhoto(cart: Cart) {
    if (cart.itemPhotoFile) {
      const image = 'data:image/jpeg;base64,' + cart.itemPhotoFile;
      return this.sanitizer.bypassSecurityTrustResourceUrl(image);
    }
    return '';
  }

  sortItems(sortOptionValue: string) {
    if (this.cartItems) {
      if (sortOptionValue === 'price_asc') {
        this.cartItems = this.cartItems.sort((a, b) => a.itemPrice - b.itemPrice);
      } else if (sortOptionValue === 'price_desc') {
        this.cartItems = this.cartItems.sort((a, b) => b.itemPrice - a.itemPrice);
      }
    }
  }

  prepareBuyProductRequest(cart: Cart): BuyProductRequest {
    return {
      itemId: cart.itemId,
      itemAmountSelected: cart.amountSelected,
      itemAmountAvailable: cart.itemAmountAvailable
    };
  }

}
