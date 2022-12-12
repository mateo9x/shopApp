import {ConfirmationService} from 'primeng/api';
import {OrderPayment} from '../order.payment.model';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderService} from '../order.service';
import {Order} from '../order.model';
import {OrderAddress} from '../order.address.model';
import {ToastService} from "../../toasts/toast.service";

@Component({
  selector: 'order-process',
  templateUrl: './order-process.component.html',
  styleUrls: ['./order-process.component.scss']
})
export class OrderProcessComponent implements OnInit {

  noData = false;
  orderId: any;
  isUserLogged = false;
  order: Order = new Order();
  orderAddress: OrderAddress = new OrderAddress();
  orderPayments: OrderPayment[] = [];
  selectedOrderPayment: OrderPayment;

  constructor(private router: Router, private route: ActivatedRoute, private orderService: OrderService, private confirmationService: ConfirmationService,
              private toastService: ToastService) {
  }

  ngOnInit() {
    this.route.params.subscribe((paramsId) => {
      this.orderId = paramsId.id;
    });
    this.orderService.findOrderById(this.orderId).subscribe((response) => {
      this.order = response;
    })
    this.orderService.findAllOrderPayments().subscribe((response) => {
      this.orderPayments = response;
    });
  }

  submitFormConfirm() {
    this.confirmationService.confirm({
      message: 'Czy na pewno chcesz kontynuować?',
      accept: () => {
        this.submitForm();
      }
    });
  }

  submitForm() {
    this.order.orderPaymentId = this.selectedOrderPayment.id;
    this.orderService.saveOrderAddress(this.orderAddress).subscribe((response) => {
      this.order.orderAddressId = response.id;
      this.orderService.updateOrder(this.order).subscribe((response) => {
        this.toastService.createSuccessToast('Sposób dostawy został wybrany');
        this.router.navigate(['profile/orders']);
      }, (error) => {
        this.toastService.createErrorToast('Nie udało się wybrać dostawy');
      });
    });
  }

}
