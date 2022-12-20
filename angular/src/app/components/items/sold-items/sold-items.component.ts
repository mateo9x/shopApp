import {Component, OnInit} from '@angular/core';
import {ItemsService} from '../items.service';
import {Item} from '../items.model';
import {ActivatedRoute} from "@angular/router";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'sold-items',
  templateUrl: './sold-items.component.html',
  styleUrls: ['./sold-items.component.scss']
})
export class SoldItemsComponent implements OnInit {

  items: Item[] = [];

  constructor(private itemService: ItemsService, private route: ActivatedRoute, private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.params.subscribe(param => {
      this.itemService.findAllItemsBySellerIdSold(param.id).subscribe((response) => {
        this.items = response;
      });
    });
  }

  getItemMainPhoto(item: Item) {
    if (item.photoFiles) {
      const image = 'data:image/jpeg;base64,' + item.photoFiles[0];
      return  this.sanitizer.bypassSecurityTrustResourceUrl(image);
    }
    return '';
  }

}
