
import { ResetPasswordComponent } from './components/user/reset-component/reset.component';
import { ItemsComponent } from './components/items/items/items.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './components/authenthication/login-guard';
import { CartComponent } from './components/cart/cart.component';
import { ProfileComponent } from './components/user/profile-component/profile.component';
import { SignInUserComponent } from './components/user/sign-in-user-component/sign-in-user.component';
import { SignUpUserComponent } from './components/user/sign-up-user-component/sign-up-user.component';
import { AnonymousUserGuard } from './components/authenthication/anonymous-user-guard';
import { NewPasswordComponent } from './components/user/new-password/new-password.component';

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'sign-in-user', component: SignInUserComponent, canActivate: [AnonymousUserGuard] },
  { path: 'sign-up-user', component: SignUpUserComponent, canActivate: [AnonymousUserGuard] },
  { path: 'cart', component: CartComponent },
  { path: 'items', component: ItemsComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'new-password',  component: NewPasswordComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [LoginGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {

 }
