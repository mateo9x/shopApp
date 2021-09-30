import { OrderAddress } from './order.address.model';
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
}
