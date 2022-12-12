import {DialogService} from 'primeng/dynamicdialog';
import {CartService} from '../../cart/cart.service';
import {Item} from '../items.model';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Cart} from "../../cart/cart.model";
import {ItemCategoryService} from "../item-category/item-category.service";
import {ToastService} from "../../toasts/toast.service";
import {UserService} from "../../user/user.service";
import {SellerService} from "../../sellers/seller.service";

@Component({
  selector: 'items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.scss']
})
export class ItemsComponent implements OnInit {

  items: Item[] = [];
  selectedItem: Item;
  cartForAnonymousUser: Cart[] = [];
  title: string;

  constructor(private itemService: ItemsService, private router: Router, private cartService: CartService,
              private toastService: ToastService, private dialogService: DialogService, private route: ActivatedRoute,
              private itemCategoryService: ItemCategoryService, private sellerService: SellerService) {
  }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      if (params['id'] !== undefined) {
        this.loadData(1, params['id']);
      } else if (params['sellerId'] !== undefined) {
        this.loadData(2, params['sellerId']);
      } else if (params['query'] !== undefined) {
        this.loadData(3, params['query']);
      }
    });
  }

  loadData(option: number, param: any) {
    if (option === 1) {
      this.itemService.findAllItemsByCategory(param).subscribe((response) => {
        this.items = response;
        this.itemCategoryService.findItemCategory(param).subscribe((response) => {
          this.title = 'Produkty z kategorii: ' + response.name;
        });
      });
    } else if (option === 2) {
      this.itemService.findAllItemsBySellerId(param).subscribe((response) => {
        this.items = response;
        this.sellerService.findSellerById(param).subscribe((response) => {
          this.title = 'Produkty sprzedawcy: ' + response.name;
        });
      });
    } else if (option === 3) {
      this.itemService.findAllBySearchQuery(param).subscribe((response) => {
        this.items = response;
        this.title = 'Produkty znalezione po frazie: ' + param;
      });
    }
  }

  addToCart(item: Item) {
    if (item.amountSelected) {
      if (sessionStorage.getItem('id_token') !== null) {
        this.cartService.addItemToCartForUser(item.id, item.amountSelected).subscribe((response) => {
          if (response) {
            this.toastService.createSuccessToast('Dodano produkt do koszyka');
          } else {
            this.toastService.createErrorToast('Produkt znajduje się już obecnie w Twoim koszyk');
          }
        });
      } else {
        if (sessionStorage.getItem('cart') !== null) {
          this.cartForAnonymousUser = JSON.parse(sessionStorage.getItem('cart') as unknown as string);
          if (this.cartForAnonymousUser.find((element) => element.itemId === item.id)) {
            this.toastService.createErrorToast('Produkt znajduje się już obecnie w Twoim koszyk');
          } else {
            this.cartForAnonymousUser.push(this.prepareCartModel(item));
            sessionStorage.setItem('cart', JSON.stringify(this.cartForAnonymousUser));
            this.toastService.createSuccessToast('Dodano produkt do koszyka');
          }
        } else {
          this.cartForAnonymousUser.push(this.prepareCartModel(item));
          sessionStorage.setItem('cart', JSON.stringify(this.cartForAnonymousUser));
          this.toastService.createSuccessToast('Dodano produkt do koszyka');
        }
      }
    } else {
      this.toastService.createErrorToast('Należy wybrać ilość przed dodaniem do koszyka');
    }
  }

  openItemDetail(itemId: number) {
    this.router.navigate(['items-details', itemId]);
  }

  getItemFirstPhoto(photoUrl: string) {
    if (photoUrl.includes(';')) {
      return photoUrl.split(';')[0];
    } else {
      return photoUrl;
    }
  }

  prepareCartModel(item: Item): Cart {
    return {
      itemId: item.id,
      itemModel: item.model,
      itemBrand: item.brand,
      itemPhotoUrl: item.photoUrl,
      itemPrice: item.price,
      itemAmountAvailable: item.amountAvailable,
      amountSelected: item.amountSelected
    }
  }

}
