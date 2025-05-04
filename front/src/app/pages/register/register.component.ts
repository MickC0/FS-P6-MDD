import { Component } from '@angular/core';
import { Router }    from '@angular/router';
import { Location }  from '@angular/common';

import { FormsModule }            from '@angular/forms';
import { CommonModule }           from '@angular/common';
import { MatFormFieldModule }     from '@angular/material/form-field';
import { MatInputModule }         from '@angular/material/input';
import { MatIconModule }          from '@angular/material/icon';
import { MatButtonModule }        from '@angular/material/button';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  hide = true;
  model = {
    username: '',
    email: '',
    password: ''
  };

  constructor(
    private location: Location,
    private router: Router
  ) {}

  goBack() {
    this.location.back();
  }

  onRegister() {
    // TODO: appeler votre service d'inscription
    console.log('Inscription avec', this.model);
    // p. ex. rediriger vers la connexion
    this.router.navigate(['/login']);
  }
}
