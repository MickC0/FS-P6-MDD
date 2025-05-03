import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {Location, NgOptimizedImage} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule }      from '@angular/material/input';
import { MatButtonModule }     from '@angular/material/button';
import { MatIconModule }       from '@angular/material/icon';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    NgOptimizedImage,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  hide = true;
  credentials = { username: '', password: '' };

  constructor(
    private location: Location,
    private router: Router
  ) {}

  goBack() {
    this.location.back();
  }

  onLogin() {
    // TODO: appeler votre service d’authentification
    console.log('Connexion avec', this.credentials);
    // par exemple, rediriger vers l’accueil
    this.router.navigate(['/']);
  }
}
