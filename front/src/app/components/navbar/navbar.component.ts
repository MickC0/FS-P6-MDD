import { Component } from '@angular/core';
import {Router, RouterLink, RouterLinkActive} from '@angular/router';
import {NgClass, NgOptimizedImage} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {SessionService} from '../../features/auth/services/session.service';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    MatIconModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    RouterLink,
    NgOptimizedImage,
    RouterLinkActive,
    NgClass,
    MatButton
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss'
})
export class NavbarComponent {
  isMenuOpen = false;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }


  logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/'], { replaceUrl: true });
  }
}
