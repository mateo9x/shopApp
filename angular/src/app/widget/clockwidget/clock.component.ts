import { Component } from "@angular/core";

@Component({
  selector: "app-clock",
  templateUrl: "./clock.component.html",
  styleUrls: ["./clock.component.css"]
})
export class ClockComponent {
  hours: string;
  minutes: string;
  seconds: string;
  private timerId: any;
  monthNames: any[];
  month: string;
  monthSelect: any;

  ngOnInit() {
    this.monthNames = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec",
    "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"];
    this.setCurrentTime();
    this.timerId = this.updateTime();

  }

  ngOnDestroy() {
    clearInterval(this.timerId);
  }

  private setCurrentTime() {
    const time = new Date(Date.now());
    this.hours = this.leftPadZero(time.getHours());
    this.minutes = this.leftPadZero(time.getMinutes());
    this.seconds = this.leftPadZero(time.getSeconds());
    this.monthSelect = time.getMonth();
    this.month = time.getDay() + ' ' + this.leftPadZero(this.monthNames[this.monthSelect]) + ' ' + time.getFullYear();
  }

  private updateTime() {
    setInterval(() => {
      this.setCurrentTime();
    }, 1000);
  }

  private leftPadZero(value: number) {
    return value < 10 ? `0${value}` : value.toString();
  }


}
