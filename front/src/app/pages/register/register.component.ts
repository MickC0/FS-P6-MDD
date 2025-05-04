import { Component }         from '@angular/core';
import { Router }            from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule }     from '@angular/material/input';
import { MatIconModule }      from '@angular/material/icon';
import { MatButtonModule }    from '@angular/material/button';
import {AuthPageComponent} from "../authpage/authpage.component";


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    AuthPageComponent,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  hide = true;
  model = { username: '', email: '', password: '' };

  constructor(private router: Router) {}

  onRegister() {
    // TODO: service d’inscription
    this.router.navigate(['/login']);
  }
}
