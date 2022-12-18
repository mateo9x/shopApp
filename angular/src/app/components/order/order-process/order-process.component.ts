import {ConfirmationService} from 'primeng/api';
import {OrderPayment} from '../order.payment.model';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {OrderService} from '../order.service';
import {Order} from '../order.model';
import {OrderAddress} from '../order.address.model';
import {ToastService} from "../../toasts/toast.service";
import {UserService} from "../../user/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'order-process',
  templateUrl: './order-process.component.html',
  styleUrls: ['./order-process.component.scss']
})
export class OrderProcessComponent implements OnInit {

  requiredForm: FormGroup;
  noData = false;
  orderId: any;
  order: Order = new Order();
  orderAddress: OrderAddress = new OrderAddress();
  orderPayments: OrderPayment[] = [];
  selectedOrderPayment: OrderPayment;

  constructor(private router: Router, private route: ActivatedRoute, private orderService: OrderService, private confirmationService: ConfirmationService,
              private toastService: ToastService, private userService: UserService, private fb: FormBuilder) {
    this.myForm();
  }

  ngOnInit() {
    this.route.params.subscribe((paramsId) => {
      this.orderId = paramsId.id;
    });
    this.orderService.findOrderById(this.orderId).subscribe((response) => {
      this.order = response;
      this.noData = false;
      this.userService.findUser(response.userId).subscribe((userResponse) => {
        this.fillStartingOrderAddressData(userResponse);
      });
    }, (error) => {
      this.toastService.createErrorToast('Zamówienie nie istnieje!');
      this.noData = true;
    });
    this.orderService.findAllOrderPayments().subscribe((response) => {
      this.orderPayments = response;
    });
  }

  myForm() {
    this.requiredForm = this.fb.group({
      firstname: ['', Validators.required ],
      lastname: ['', Validators.required ],
      mail: ['', Validators.required ],
      street: ['', Validators.required ],
      streetNumber: ['', Validators.required ],
      postalCode: ['', Validators.required ],
      city: ['', Validators.required ],
      phoneNumber: ['', Validators.required ],
      orderPayment: ['', Validators.required ]
    });
  }

  formConfirm() {
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
      this.orderService.finishOrderProcess(this.order).subscribe((response) => {
        this.toastService.createSuccessToast('Sposób dostawy został wybrany');
        this.router.navigate(['profile/orders']);
      }, (error) => {
        this.toastService.createErrorToast('Nie udało się wybrać dostawy');
      });
    });
  }

  fillStartingOrderAddressData(user: any) {
    this.orderAddress.firstname = user.firstName;
    this.orderAddress.lastname = user.lastName;
    this.orderAddress.mail = user.mail;
    this.orderAddress.street = user.street;
    this.orderAddress.streetNumber = user.streetNumber;
    this.orderAddress.city = user.city;
  }

  getTotalAmountToPay() {
    return this.order.amountBought * this.order.itemPrice;
  }
}
