import { UserService } from './../user/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { Cart } from './cart.model';
import { CartService } from './cart.service';

@Component({
  selector: 'cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cols: any[];
  selectedCartItem: Cart;
  cartItems: Cart[] = [];
  noData = true;
  constructor(private cartService: CartService, private router: Router, private messageService: MessageService, private userService: UserService) { }

  ngOnInit() {
    this.userService.isUserLogged().subscribe((response) => {
      if (response.username !== 'anonymousUser') {
        this.cartService.findCartForUser(response.id).subscribe((response) => {
          if (response.length > 0) {
            this.cartItems = response;
            this.noData = false;
          } else {
            this.noData = true;
          }
        });
      }
    });

    this.cols = [
      { field: 'itemBrand', header: 'Marka' },
      { field: 'itemModel', header: 'Nazwa' },
      { field: 'itemPrice', header: 'Cena' }
    ];


  }


}
