import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { CartService } from './cart.service';

@Component({
  selector: 'cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cols: any[];

  constructor(private cartService: CartService, private router: Router, private messageService: MessageService) { }

  ngOnInit() {
    this.cartService.findCartForUser().subscribe((response) => {
      console.log('Cart', response);
    });
  }


}
