import { ProfileOrdersComponent } from './components/user/profile-orders-component/profile-orders.component';
import { AnonymousUserGuard } from './components/authenthication/anonymous-user-guard';
import { ResetPasswordComponent } from './components/user/reset-component/reset.component';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import {ToastModule} from 'primeng/toast';
import { SignUpUserComponent } from './components/user/sign-up-user-component/sign-up-user.component';
import { SignInUserComponent } from './components/user/sign-in-user-component/sign-in-user.component';
import { ConfirmationService, MessageService } from 'primeng/api';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClockComponent } from './widget/clockwidget/clock.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TooltipModule } from 'primeng/tooltip';
import { CartComponent } from './components/cart/cart.component';
import { ItemCategoryComponent } from './components/items/item-category/item-category.component';
import { DropdownModule } from 'primeng/dropdown';
import { AppInterceptor } from './components/authenthication/app-interceptor';
import { ProfileComponent } from './components/user/profile-component/profile.component';
import { LoginGuard } from './components/authenthication/login-guard';
import { ItemsComponent } from './components/items/items/items.component';
import { NewPasswordComponent } from './components/user/new-password/new-password.component';
import { DynamicDialogModule, DialogService } from 'primeng/dynamicdialog';
import { ItemsDetailsComponent } from './components/items/items-details/items-details.component';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { OrderProcessComponent } from './components/order/order-process/order-process.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInUserComponent,
    SignUpUserComponent,
    CartComponent,
    ClockComponent,
    ItemCategoryComponent,
    ProfileComponent,
    ItemsComponent,
    ItemsDetailsComponent,
    ResetPasswordComponent,
    NewPasswordComponent,
    OrderProcessComponent,
    ProfileOrdersComponent

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    TableModule,
    ToastModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
    TooltipModule,
    DropdownModule,
    FormsModule,
    DynamicDialogModule,
    ConfirmDialogModule

  ],
  providers: [MessageService, { provide: HTTP_INTERCEPTORS, useClass: AppInterceptor, multi: true }, LoginGuard, AnonymousUserGuard, DialogService, ConfirmationService],
  bootstrap: [AppComponent],
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class AppModule { }
