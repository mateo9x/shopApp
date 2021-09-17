import { EventEmitter } from "@angular/core";

export class ItemCompsService {

  navchange: EventEmitter<number> = new EventEmitter();
  constructor() { }

  emitNavChangeEvent(number: number) {
    this.navchange.emit(number);
  }

  getNavChangeEmitter() {
    return this.navchange;
  }

}
