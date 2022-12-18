import {DialogService} from 'primeng/dynamicdialog';
import {CartService} from '../../cart/cart.service';
import {Item} from '../items.model';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Cart} from "../../cart/cart.model";
import {ItemCategoryService} from "../item-category/item-category.service";
import {ToastService} from "../../toasts/toast.service";
import {SellerService} from "../../sellers/seller.service";
import {DomSanitizer} from "@angular/platform-browser";

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
  sortOptions = [{name: 'Cena rosnąco', value: 'price_asc'},
    {name: 'Cena malejąco', value: 'price_desc'},
    {name: 'Najnowsze', value: 'latest'},
    {name: 'Najstarsze', value: 'oldest'}];
  selectedSortOption: any;

  constructor(private itemService: ItemsService, private router: Router, private cartService: CartService,
              private toastService: ToastService, private dialogService: DialogService, private route: ActivatedRoute,
              private itemCategoryService: ItemCategoryService, private sellerService: SellerService,
              private sanitizer: DomSanitizer) {
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
      this.itemService.findAllItemsBySellerIdActive(param).subscribe((response) => {
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

  getItemMainPhoto(item: Item) {
    if (item.photoFiles) {
      const image = 'data:image/jpeg;base64,' + item.photoFiles[0];
      return this.sanitizer.bypassSecurityTrustResourceUrl(image);
    }
    return '';
  }

  sortItems(sortOptionValue: string) {
    if (this.items) {
      if (sortOptionValue === 'price_asc') {
        this.items = this.items.sort((a, b) => a.price - b.price);
      } else if (sortOptionValue === 'price_desc') {
        this.items = this.items.sort((a, b) => b.price - a.price);
      } else if (sortOptionValue === 'latest') {
        this.items = this.items.sort((a, b) => b.createDate.localeCompare(a.createDate));
      } else if (sortOptionValue === 'oldest') {
        this.items = this.items.sort((a, b) => a.createDate.localeCompare(b.createDate));
      }
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
      amountSelected: item.amountSelected,
      itemPhotoFile: item.photoFiles[0]
    }
  }

}
