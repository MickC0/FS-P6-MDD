import { Component } from '@angular/core';
import {MatButton} from '@angular/material/button';
import {Router, RouterLink, RouterModule} from '@angular/router';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    MatButton,
    NgOptimizedImage,
    RouterLink
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export class AuthComponent {

}
