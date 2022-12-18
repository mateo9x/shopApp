import {Component, OnInit} from '@angular/core';
import {User} from 'src/app/components/user/user.model';
import {UserService} from 'src/app/components/user/user.service';
import {SellerService} from "../../sellers/seller.service";

@Component({
  selector: 'profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  user: User = new User;
  sellerId: number;

  constructor(private userService: UserService, private sellerService: SellerService) {
  }

  ngOnInit() {
    this.userService.getUserLogged().subscribe((response) => {
      this.user = response;
      this.sellerService.findSellerByUserLogged().subscribe((sellerResponse) => {
        if (sellerResponse) {
          this.sellerId = sellerResponse.id;
        }
      });
    });

  }

}
