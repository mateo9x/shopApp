
export class Order {
  id: number;
  itemId: number;
  itemBrand: string;
  itemModel: string;
  itemPrice: number;
  date: Date;
  userId: number;
  username: string;
  orderAddressId: number;
  orderPaymentId: number;
  orderPaymentType: string;
  paymentSelected: boolean;
  returnMaxDate: any;
  sellerName: string;
  photoUrl: string;
  amountBought: number;
}
