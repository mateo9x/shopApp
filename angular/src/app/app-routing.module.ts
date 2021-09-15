import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginGuard } from './components/authenthication/login-guard';
import { CartComponent } from './components/cart/cart.component';
import { ProfileComponent } from './components/user/profile-component/profile.component';
import { SignInUserComponent } from './components/user/sign-in-user-component/sign-in-user.component';
import { SignUpUserComponent } from './components/user/sign-up-user-component/sign-up-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'sign-in-user', component: SignInUserComponent },
  { path: 'sign-up-user', component: SignUpUserComponent },
  { path: 'cart', component: CartComponent },
  { path: 'profile', component: ProfileComponent, canActivate: [LoginGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
