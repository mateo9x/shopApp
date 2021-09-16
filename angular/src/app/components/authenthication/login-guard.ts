import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { UserService } from '../user/user.service';

@Injectable()
export class LoginGuard implements CanActivate {
    constructor(public auth: UserService, protected router: Router) { }

    canActivate() {
        if (!this.auth.isUserLogged()) {
            this.router.navigate(['/']);
            return false;
        }
        return true;
    }
}