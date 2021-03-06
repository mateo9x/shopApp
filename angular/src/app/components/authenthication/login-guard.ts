import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { UserService } from '../user/user.service';

@Injectable()
export class LoginGuard implements CanActivate {
  constructor(protected router: Router) { }

  canActivate() {
    if ( sessionStorage.getItem('id_token') === null) {
      this.router.navigate(['/']);
      return false;
    } else {
      return true;
    }
  }
}
