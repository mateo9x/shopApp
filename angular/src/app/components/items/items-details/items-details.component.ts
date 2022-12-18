import {UserService} from 'src/app/components/user/user.service';
import {ConfirmationService} from 'primeng/api';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';
import {Order} from '../../order/order.model';
import * as moment from "moment/moment";
import {ToastService} from "../../toasts/toast.service";
import {BuyProductRequest, BuyProductService} from "../buy.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'items-details',
  templateUrl: './items-details.component.html',
  styleUrls: ['./items-details.component.scss']
})
export class ItemsDetailsComponent implements OnInit {

  item: Item = new Item();
  cols: any[];
  noData = false;
  itemId: any;
  isUserLogged = false;
  order: Order = new Order();
  productSold = false;
  carouselPhotos: any[] = [];

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService,
              private toastService: ToastService, private confirmationService: ConfirmationService, private route: ActivatedRoute,
              private buyProductService: BuyProductService, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.params.subscribe(paramsId => {
      this.itemId = paramsId.id;
    });
    this.itemService.findItem(this.itemId).subscribe((response) => {
      if (!response.description) {
        response.description = 'Brak opisu';
      }
      if (response.amountAvailable.length === 0) {
        this.productSold = true;
      }
      this.item = response;
      let id = 1;
      this.item.photoFiles.forEach((photo) => {
        this.carouselPhotos.push({id: id, src: this.getItemPhoto(photo)});
        id++;
      });
    });
    this.userService.isUserLogged().subscribe((response) => {
      if (response !== null) {
        this.isUserLogged = true;
      }
    })
  }

  buyConfirm() {
    if (this.item.amountSelected) {
      this.confirmationService.confirm({
        message: 'Czy na pewno chcesz zakupić ten produkt?',
        accept: () => {
          this.buyProduct();
        }
      });
    } else {
      this.toastService.createErrorToast('Należy wybrać ilość produktów!');
    }
  }

  buyProduct() {
    this.buyProductService.buyProduct(this.prepareBuyProductRequest(this.item));
  }

  showSellerItems() {
    this.router.navigate(['items/seller', this.item.sellerId]);
  }

  formatCreateDate(createDate: string) {
    return moment.utc(createDate).format('YYYY-MM-DD HH:mm');
  }

  getItemPhoto(photo: any) {
    if (photo) {
      const image = 'data:image/jpeg;base64,' + photo;
      return this.sanitizer.bypassSecurityTrustResourceUrl(image);
    }
    return '';
  }

  prepareBuyProductRequest(item: Item): BuyProductRequest {
    return {
      itemId: item.id,
      itemAmountSelected: item.amountSelected,
      itemAmountAvailable: item.amountAvailable
    };
  }

}
