import { Component } from '@angular/core';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-back-arrow',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './back-arrow.component.html',
  styleUrl: './back-arrow.component.scss'
})
export class BackArrowComponent {
  navigateBack() {
    window.history.back();
  }
}
