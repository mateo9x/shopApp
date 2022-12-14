import {Injectable} from "@angular/core";
import {CartService} from "../cart/cart.service";
import {ToastService} from "../toasts/toast.service";
import {OrderService} from "../order/order.service";
import {Router} from "@angular/router";
import {Cart, CartUpdateRequest} from "../cart/cart.model";
import {Order} from "../order/order.model";

@Injectable({
  providedIn: 'root'
})
export class BuyProductService {

  constructor(private cartService: CartService, private toastService: ToastService,
              private orderService: OrderService, private router: Router) {
  }

  public buyProduct(request: BuyProductRequest) {
    let cartItems: Cart[] = [];
    let order: Order = new Order();
    const amountAvailableAfterBuy = request.itemAmountAvailable - request.itemAmountSelected;
    order.date = new Date();
    order.itemId = request.itemId;
    order.amountBought = request.itemAmountSelected;
    this.orderService.createOrder(order).subscribe((response) => {
      if (response !== null) {
        this.cartService.updateItemAmountCartAfterBuy(this.prepareRequestModelForCartItemAmountUpdate(request.itemId, amountAvailableAfterBuy)).subscribe((itemAmountUpdated) => {
          if (sessionStorage.getItem('cart') !== null) {
            cartItems = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
            cartItems.forEach((element, index) => {
              if (element.itemId === request.itemId) {
                if (amountAvailableAfterBuy === 0) {
                  delete cartItems[index];
                } else if (amountAvailableAfterBuy > 0 && cartItems[index].amountSelected > amountAvailableAfterBuy) {
                  cartItems[index].amountSelected = amountAvailableAfterBuy;
                }
              }
            });
            sessionStorage.setItem('cart', JSON.stringify(cartItems));
          }
          this.toastService.createSuccessToast('Produkt został zakupiony');
          this.router.navigate(['order-process', response.id]);
        });
      } else {
        this.toastService.createErrorToast('Musisz być zalogowany, żeby kupować produkty');
      }
    });
  }

  prepareRequestModelForCartItemAmountUpdate(itemId: number, amountAvailableAfterBuy: number): CartUpdateRequest {
    return new CartUpdateRequest(itemId, amountAvailableAfterBuy);
  }

}

export class BuyProductRequest {
  itemId: number;
  itemAmountAvailable: number;
  itemAmountSelected: number;
}
