import {Component, OnInit} from '@angular/core';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';
import {ActivatedRoute, Router} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'selling-items',
  templateUrl: './selling-items.component.html',
  styleUrls: ['./selling-items.component.scss']
})
export class SellingItemsComponent implements OnInit {

  items: Item[] = [];

  constructor(private itemService: ItemsService, private route: ActivatedRoute, private router: Router, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.params.subscribe(param => {
      this.itemService.findAllItemsBySellerIdActive(param.id).subscribe((response) => {
        this.items = response;
      });
    });
  }

  openItemDetail(itemId: number) {
    this.router.navigate(['items-details', itemId]);
  }

  getItemMainPhoto(item: Item) {
    if (item.photoFiles) {
      const image = 'data:image/jpeg;base64,' + item.photoFiles[0];
      return  this.sanitizer.bypassSecurityTrustResourceUrl(image);
    }
    return '';
  }

}
