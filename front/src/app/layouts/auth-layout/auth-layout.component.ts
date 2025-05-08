import {Component, Input} from '@angular/core';
import {BackArrowComponent} from '../../components/back-arrow/back-arrow.component';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-auth-layout',
  imports: [
    BackArrowComponent,
    NgOptimizedImage
  ],
  templateUrl: './auth-layout.component.html',
  styleUrl: './auth-layout.component.scss'
})
export class AuthLayoutComponent {
  @Input() title: string = '';
}
