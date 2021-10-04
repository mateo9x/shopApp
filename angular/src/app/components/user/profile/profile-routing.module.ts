
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from '../../authenthication/login-guard';
import { ProfileDataComponent } from './profile-data-component/profile-data.component';
import { ProfileOrdersComponent } from './profile-orders-component/profile-orders.component';


const profileRoutes: Routes = [
  { path: 'data', component: ProfileDataComponent, canActivate: [LoginGuard] },
  { path: 'orders', component: ProfileOrdersComponent, canActivate: [LoginGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(profileRoutes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {

 }
