import {ItemCategoryService} from '../item-category/item-category.service';
import {ItemCategory} from '../item-category/item-category.model';
import {UserService} from 'src/app/components/user/user.service';
import {ConfirmationService} from 'primeng/api';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';
import {SellerService} from "../../sellers/seller.service";
import {Seller} from "../../sellers/seller.model";
import {ToastService} from "../../toasts/toast.service";
import {UntypedFormBuilder, UntypedFormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'sell-items',
  templateUrl: './sell-items.component.html',
  styleUrls: ['./sell-items.component.scss']
})
export class SellItemsComponent implements OnInit {

  item: Item = new Item();
  selectedCategory: ItemCategory;
  categories: ItemCategory[] = [];
  photos: File[] = [];
  fileAmountLimit = 5;
  requiredForm: UntypedFormGroup;

  constructor(private itemService: ItemsService, private router: Router, private userService: UserService,
              private confirmationService: ConfirmationService, private itemCategoryService: ItemCategoryService,
              private sellerService: SellerService, private toastService: ToastService, private fb: UntypedFormBuilder) {
    this.myForm();
  }

  ngOnInit() {
    this.itemCategoryService.findAllItemCategories().subscribe((response) => {
      this.categories = response;
    });
  }

  myForm() {
    this.requiredForm = this.fb.group({
      brand: ['', Validators.required],
      model: ['', Validators.required],
      selectedCategory: ['', Validators.required],
      price: ['', Validators.required],
      amountAvailable: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  uploadFile(event: any) {
    for (let file of event.files) {
      const photoExist = this.photos.find((photo) => photo.name === file.name && photo.type === file.type);
      if (photoExist) {
        this.toastService.createErrorToast('Zdjęcie o takiej nazwie i takim formacie zostało już dodane!');
      } else {
        this.photos.push(file);
      }
    }
  }

  deleteFile(event: any) {
    this.photos = this.photos.filter((photo) => photo.name !== event.file.name && photo.type !== event.file.type);
  }

  addProductConfirm() {
    this.confirmationService.confirm({
      key: 'sell',
      header: 'Wystawienie przedmiotu',
      message: 'Czy na pewno chcesz wystawić ten produkt na sprzedaż?',
      accept: () => {
        this.findSellerAndAddProduct();
        this.confirmationService.close();
      }
    });
  }

  addProduct(seller: Seller) {
    if (seller) {
      this.item.sellerId = seller.id;
      this.item.itemCategoryId = this.selectedCategory.id;
      if (this.photos.length > 0) {
        this.item.photoUrl = this.photos.map(photo => photo.name).join(';');
      }
      this.itemService.doesItemAlreadyExists(this.item).subscribe((itemExistsResponse) => {
        const itemExists = itemExistsResponse as boolean;
        if (itemExists) {
          this.toastService.createErrorToast('Taki produkt jest juz wystawiony przez Ciebie na sprzedaż !');
        } else {
          this.itemService.saveItem(this.item, this.photos).subscribe((response) => {
            if (response) {
              this.toastService.createSuccessToast('Dodano produkt do sprzedaży !');
              this.router.navigate(['items-details', response.id]);
            } else {
              this.toastService.createErrorToast('Wystąpił błąd przy dodawaniu produktu na sprzedaż');
            }
          }, (error) => {
            this.toastService.createErrorToast('Wystąpił błąd przy dodawaniu produktu na sprzedaż');
          });
        }
      });
    }
  }

  findSellerAndAddProduct() : void {
    this.sellerService.findSellerByUserLogged().subscribe((sellerResponse) => {
      if (sellerResponse === null) {
        this.createSellerAccountConfirm();
      } else {
        this.addProduct(sellerResponse);
      }
    });
  }

  createSellerAccountConfirm() {
    this.confirmationService.confirm({
      key: 'seller',
      header: 'Konto sprzedawcy',
      message: 'Nie posiadasz konta sprzedawcy. Czy założyć konto i wystawić produkt na sprzedaż ?',
      accept: () => {
        this.createSellerAccountAndSellProduct();
      }
    });
  }

  createSellerAccountAndSellProduct() {
    this.sellerService.saveNewSeller().subscribe((newSellerResponse) => {
      this.addProduct(newSellerResponse);
    });
  }

}
