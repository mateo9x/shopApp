export class Cart {
  id?: number;
  userId?: number;
  itemId: number;
  itemBrand: string;
  itemModel: string;
  itemPrice: number;
  itemPhotoUrl: string;
  itemAmountAvailable: number;
  amountSelected: number;
  itemPhotoFiles: any[];
}

export class CartUpdateRequest {
  itemId: number;
  amountAvailableAfterBuy: number;

  constructor(itemId: number, amountAvailableAfterBuy: number) {
    this.itemId = itemId;
    this.amountAvailableAfterBuy = amountAvailableAfterBuy;
  }
}
