import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInUserComponent } from './components/user/sign-in-user-component/sign-in-user.component';
import { SignUpUserComponent } from './components/user/sign-up-user-component/sign-up-user.component';

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'sign-in-user', component: SignInUserComponent },
  { path: 'sign-up-user', component: SignUpUserComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
