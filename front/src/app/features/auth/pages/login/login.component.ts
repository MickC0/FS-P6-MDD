import { Component } from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import { LoginRequest } from '../../interfaces/LoginRequest';
import {AuthSuccess} from '../../interfaces/AuthSuccess';
import {User} from '../../../user/interfaces/User.interface';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {SessionService} from '../../services/session.service';
import {AuthLayoutComponent} from '../../../../layouts/auth-layout/auth-layout.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    AuthLayoutComponent,
    MatButtonModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatIcon,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  public showPassword: boolean = false;
  public onErrorSubmit: boolean = false;

  private authService: AuthService;

  constructor(
    authService: AuthService,
    private router: Router,
    private sessionService: SessionService
  ) {
    this.authService = authService;
  }

  //--- FORM CONTROLS ---
  public loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,60}$'),
    ]),
  });

  // --- SUBMIT ---
  public onSubmit(): void {
    let temp = this.loginForm.value;
    const loginRequest = temp as LoginRequest;

    this.authService.login(loginRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
          this.router.navigate(['/feed']);
        });
      },
      error: () => {
        this.onErrorSubmit = true;
      },
    });
  }
}
