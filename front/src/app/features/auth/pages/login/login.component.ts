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
import {MatIconModule} from '@angular/material/icon';
import {take} from 'rxjs';
import {NgIf} from '@angular/common';
import {MatSnackBar} from '@angular/material/snack-bar';

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
    MatIconModule,
    NgIf,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  public showPassword: boolean = false;
  public onErrorSubmit: boolean = false;

  ngOnInit() {
    this.emailControl.valueChanges.subscribe(() => this.onErrorSubmit = false);
    this.passwordControl.valueChanges.subscribe(() => this.onErrorSubmit = false);
  }

  constructor(
    private authService: AuthService,
    private router: Router,
    private sessionService: SessionService,
    private snackBar: MatSnackBar
  ) {}

  public loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,60}$'),
    ]),
  });

  get emailControl(): FormControl {
    return this.loginForm.get('email') as FormControl;
  }

  get passwordControl(): FormControl {
    return this.loginForm.get('password') as FormControl;
  }

  public onSubmit(): void {
    this.onErrorSubmit = false;  // reset avant chaque tentative

    const loginRequest = this.loginForm.value as LoginRequest;

    this.authService.login(loginRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: AuthSuccess) => {
          localStorage.setItem('token', response.token);
          this.authService.me()
            .pipe(take(1))
            .subscribe((user: User) => {
              this.sessionService.logIn(user);
              this.router.navigate(['/feed']);
            });
        },
        error: () => {
          this.snackBar.open(
            'Email ou mot de passe incorrect',
            'Fermer',
            {
              duration: 5000,
              panelClass: ['snackbar-error'],
              verticalPosition: 'top'
            }
          );
        }
      });
  }
}
