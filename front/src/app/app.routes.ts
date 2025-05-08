import { Routes } from '@angular/router';

import {AuthComponent} from './features/auth/pages/auth/auth.component';
import {TopicsComponent} from './features/topics/pages/topics/topics.component';
import {ProfileComponent} from './features/user/pages/profile/profile.component';
import {LoginComponent} from './features/auth/pages/login/login.component';
import {RegisterComponent} from './features/auth/pages/register/register.component';
import {PostsComponent} from './features/posts/pages/posts/posts.component';
import {PostDetailComponent} from './features/posts/pages/post-detail/post-detail.component';
import {NewPostComponent} from './features/posts/pages/new-post/new-post.component';

export const routes: Routes = [
  { path: '', component: AuthComponent },
  { path: 'login', title: 'Se connecter', component: LoginComponent },
  { path: 'register', title: "S'inscrire", component: RegisterComponent },
  { path: 'topics', title: 'Les thèmes', component: TopicsComponent },
  { path: 'profile', title: 'Mon profil', component: ProfileComponent },
  { path: 'feed', title: 'Bienvenue sur MDD', component: PostsComponent },
  { path: 'post/:id', title: 'Bonne lecture', component: PostDetailComponent },
  { path: 'create-post', title: 'Créer un article', component: NewPostComponent }
];
