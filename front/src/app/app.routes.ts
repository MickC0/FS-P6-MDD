import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {MeComponent} from './pages/me/me.component';
import {ArticlepageComponent} from './pages/articlepage/articlepage.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login',   component: LoginComponent },
  { path: 'register',  component: RegisterComponent },
  { path: 'me', component: MeComponent },
  { path: 'articles', component: ArticlepageComponent },
];
