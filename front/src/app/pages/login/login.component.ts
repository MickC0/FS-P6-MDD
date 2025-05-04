import { Component }        from '@angular/core';
import { Router }           from '@angular/router';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule }     from '@angular/material/input';
import { MatIconModule }      from '@angular/material/icon';
import { MatButtonModule }    from '@angular/material/button';
import {AuthPageComponent} from '../authpage/authpage.component';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    AuthPageComponent,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
  ],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  hide = true;
  credentials = { username: '', password: '' };

  constructor(private router: Router) {}

  onLogin() {
    // TODO: service d’authentification
    this.router.navigate(['/']);
  }
}
