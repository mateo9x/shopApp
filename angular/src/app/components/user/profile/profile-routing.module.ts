
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from '../../authenthication/login-guard';
import { SellItemsComponent } from '../../items/sell-items/sell-items.component';
import { ProfileDataComponent } from './profile-data-component/profile-data.component';
import { ProfileOrdersComponent } from './profile-orders-component/profile-orders.component';
import {SoldItemsComponent} from "../../items/sold-items/sold-items.component";
import {SellingItemsComponent} from "../../items/selling-items/selling-items.component";


const profileRoutes: Routes = [
  { path: 'data', component: ProfileDataComponent, canActivate: [LoginGuard] },
  { path: 'orders', component: ProfileOrdersComponent, canActivate: [LoginGuard] },
  { path: 'sell-items',  component: SellItemsComponent },
  { path: 'selling-items/:id',  component: SellingItemsComponent },
  { path: 'sold-items/:id',  component: SoldItemsComponent }
];

@NgModule({
  imports: [RouterModule.forChild(profileRoutes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {

 }
