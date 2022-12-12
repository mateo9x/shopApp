import {Order} from '../../../order/order.model';
import {OrderService} from '../../../order/order.service';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ConfirmationService} from 'primeng/api';
import {User} from 'src/app/components/user/user.model';
import {UserService} from 'src/app/components/user/user.service';
import {ToastService} from "../../../toasts/toast.service";

@Component({
  selector: 'profile-orders',
  templateUrl: './profile-orders.component.html',
  styleUrls: ['./profile-orders.component.scss']
})
export class ProfileOrdersComponent implements OnInit {

  user: User = new User();
  cols: any[];
  loading: boolean;
  orders: Order[] = [];

  constructor(private userService: UserService, private router: Router, private toastService: ToastService,
              private orderService: OrderService, private confirmationService: ConfirmationService) {
  }

  ngOnInit() {
    this.userService.getUserLogged().subscribe((response) => {
      this.user = response;
      this.orderService.findOrdersForUser(response.id).subscribe((response) => {
        response.forEach((res) => {
          const endDate = new Date(res.date);
          res.returnMaxDate = endDate.setDate(endDate.getDate() + 7);
          if (res.orderPaymentType === null || res.orderPaymentType === undefined) {
            res.paymentSelected = false;
          } else {
            res.paymentSelected = true;
          }
        });
        this.orders = response;
      });
    });

  }

  paymentContinue(id: number) {
    this.router.navigate(['order-process', id]);
  }

  checkOrderReturnDate(order: Order) {
    const orderMaxExp = new Date(order.date);
    orderMaxExp.setDate(orderMaxExp.getDate() + 7);
    return new Date() > orderMaxExp;
  }

  returnProductConfirm(order: Order) {
    const itemFullName = order.itemBrand + ' ' + order.itemModel;
    this.confirmationService.confirm({
      message: 'Czy na pewno chcesz zwrócić produkt ' + itemFullName + ' ? Wykonanie zwrotu dotyczy wszystkich zakupionych sztuk produktu ('+ order.amountBought + ' szt.)',
      accept: () => {
        this.returnProduct(order.id, order.amountBought);
      }
    });
  }

  returnProduct(id: number, amountOfProductsToReturn: number) {
    this.orderService.returnProduct(id, amountOfProductsToReturn).subscribe((response) => {
      this.toastService.createSuccessToast('Produkty został zwrócony');
      this.ngOnInit();
    });
  }

  // const ref = this.dialogService.open(ProfileOrderDialogComponent, {
  //   header: 'Szczegóły produktu ' + item.brand + ' ' + item.model,
  //   data: { item: Object.assign({}, item) },
  //   width: '50%'
  // });
}
