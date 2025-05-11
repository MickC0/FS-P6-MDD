import {Component, OnInit} from '@angular/core';
import { Topic } from '../../../topics/interfaces/Topic.interface';
import { SessionService } from '../../../auth/services/session.service';
import { AuthService } from '../../../auth/services/auth.service';
import { Router } from '@angular/router';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { take } from 'rxjs';
import { User } from '../../interfaces/User.interface';
import { UpdateRequest } from '../../interfaces/UpdateRequest.interface';
import { AuthSuccess } from '../../../auth/interfaces/AuthSuccess';
import {MatFormField, MatInput, MatLabel, MatSuffix} from '@angular/material/input';
import {NavLayoutComponent} from '../../../../layouts/nav-layout/nav-layout.component';
import {MatButton, MatIconButton} from '@angular/material/button';
import {GridLayoutComponent} from '../../../../layouts/grid-layout/grid-layout.component';
import {TopicCardComponent} from '../../../topics/components/topic-card/topic-card.component';
import {NgForOf} from '@angular/common';
import { MatIconModule } from '@angular/material/icon'


@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInput,
    NavLayoutComponent,
    MatIconButton,
    MatButton,
    GridLayoutComponent,
    TopicCardComponent,
    NgForOf,
    MatSuffix,
    MatIconModule,
    MatLabel,
    MatFormField,

  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{
  public topics: Topic[] = [];
  public showOldPassword: boolean = false;
  public showPassword: boolean = false;
  public showPasswordConfirmation: boolean = false;
  public onErrorSubmit: boolean = false;
  userSubscriptions: Topic[] = [];

  constructor(
    protected sessionService: SessionService,
    private authService: AuthService,
  ) {
    this.sessionService = sessionService;
    this.authService = authService;
  }

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


  ngOnInit(): void {
    this.authService.me().pipe(take(1)).subscribe(user => {
      this.sessionService.logIn(user);
      this.userSubscriptions = user.subscriptions;
      this.profileForm.setValue({
        name:        user.name,
        email:       user.email,
        oldPassword: '',
        password:    '',
        password2:   ''
      });
    });
  }

  onSubscriptionsChanged(): void {
    this.userSubscriptions = this.sessionService.user?.subscriptions ?? [];
  }

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
