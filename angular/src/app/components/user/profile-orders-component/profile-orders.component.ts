import { Order } from './../../order/order.model';
import { OrderService } from './../../order/order.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { User } from 'src/app/components/user/user.model';
import { UserService } from 'src/app/components/user/user.service';

@Component({
  selector: 'profile-orders',
  templateUrl: './profile-orders.component.html',
  styleUrls: ['./profile-orders.component.scss']
})
export class ProfileOrdersComponent implements OnInit {

  user: User = new User();
  cols: any[];
  loading: boolean;
  changePswdForm = false;
  orders: Order[] = [];

  constructor(private userService: UserService, private router: Router, private messageService: MessageService, private orderService: OrderService) { }

  ngOnInit() {
    this.userService.getUserLogged().subscribe((response) => {
      this.user = response;
    });
    this.orderService.findOrdersForUser(this.user.id).subscribe((response) => {
      this.orders = response;
    });

  }





}
