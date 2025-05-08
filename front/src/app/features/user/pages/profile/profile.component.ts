import { Component } from '@angular/core';
import {SessionService} from '../../../auth/services/session.service';
import {AuthService} from '../../../auth/services/auth.service';
import { Router } from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {take} from 'rxjs';
import {User} from '../../interfaces/User.interface';
import {UpdateRequest} from '../../interfaces/UpdateRequest.interface';
import {AuthSuccess} from '../../../auth/interfaces/AuthSuccess';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
  public topics: Topic[] = [];
  public showOldPassword: boolean = false;
  public showPassword: boolean = false;
  public showPasswordConfirmation: boolean = false;
  public onErrorSubmit: boolean = false;
  userSubscriptions: Topic[] = [];

  constructor(
    private sessionService: SessionService,
    private authService: AuthService,
    private router: Router
  ) {
    this.router = router;
    this.sessionService = sessionService;
    this.authService = authService;
  }

  // --- FORM CONTROLS ---
  public profileForm = new FormGroup({
    name: new FormControl('', [
      Validators.minLength(3),
      Validators.maxLength(20),
    ]),
    email: new FormControl('', [Validators.email]),
    oldPassword: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.maxLength(60),
    ]),
    password: new FormControl('', [
      Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,60}$'),
    ]),
    password2: new FormControl('', [
      Validators.pattern('^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,60}$'),
    ]),
  });

  /**
   * Fetch the current user's subscriptions and the update profile's form values.
   *
   * @return {void}
   */
  public ngOnInit(): void {
    this.authService
      .me()
      .pipe(take(1))
      .subscribe((user: User) => {
        this.userSubscriptions = user.subscriptions;

        this.profileForm.setValue({
          email: user.email,
          name: user.name,
          oldPassword: '',
          password: '',
          password2: '',
        });
      });
  }

  logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  // --- SUBMIT ---
  onSubmit(): void {
    const updateProfileRequest = this.profileForm.value as UpdateRequest;
    this.authService.update(updateProfileRequest).subscribe({
      next: (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().subscribe((user: User) => {
          this.sessionService.logIn(user);
        });
        this.profileForm.patchValue({
          oldPassword: '',
          password: '',
          password2: '',
        });
      },
      error: (_) => {
        this.onErrorSubmit = true;
      },
    });
  }
}
