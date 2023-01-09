import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";
import {Order} from "../../order/order.model";
import {OrderService} from "../../order/order.service";

@Component({
  selector: 'sold-items',
  templateUrl: './sold-items.component.html',
  styleUrls: ['./sold-items.component.scss']
})
export class SoldItemsComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService, private route: ActivatedRoute, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.params.subscribe(param => {
      this.orderService.findOrdersForSeller(param.id).subscribe((response) => {
        this.orders = response;
      });
    });
  }

  getItemMainPhoto(order: Order) {
    if (order.photo) {
      const image = 'data:image/jpeg;base64,' + order.photo
      return this.sanitizer.bypassSecurityTrustResourceUrl(image);
    }
    return '';
  }

}
