import { Component } from '@angular/core';
import {PostCardComponent} from '../../components/post-card/post-card.component';
import {GridLayoutComponent} from '../../../../layouts/grid-layout/grid-layout.component';
import {MatButton} from '@angular/material/button';
import {RouterLink} from '@angular/router';
import {MatMenu, MatMenuItem, MatMenuTrigger} from '@angular/material/menu';
import {NavLayoutComponent} from '../../../../layouts/nav-layout/nav-layout.component';
import {Post} from '../../interfaces/Post.interface';
import {User} from '../../../user/interfaces/User.interface';
import {PostsService} from '../../services/posts.service';
import {SessionService} from '../../../auth/services/session.service';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-posts',
  standalone: true,
  imports: [
    PostCardComponent,
    GridLayoutComponent,
    MatButton,
    RouterLink,
    MatMenu,
    MatMenuTrigger,
    NavLayoutComponent,
    MatMenuItem,
    NgIf,
    NgForOf
  ],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.scss'
})
export class PostsComponent {
  posts: Post[] = [];
  user?: User;

  constructor(
    private postsService: PostsService,
    private sessionService: SessionService
  ) {
  }

  ngOnInit(): void {
    this.user = this.sessionService.user;
    this.postsService.getAllPosts(this.user!.id).subscribe({
      next: (posts) =>
        (this.posts = posts.sort(
          (a, b) =>
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
        )),
    });
  }

  sortPosts(sortBy: string) {
    if (this.posts) {
      if (sortBy === 'date') {
        this.posts = this.posts.sort(
          (a, b) =>
            new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime()
        );
      } else if (sortBy === 'date_reverse') {
        this.posts = this.posts.sort(
          (a, b) =>
            new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime()
        );
      } else if (sortBy === 'title') {
        this.posts = this.posts.sort((a, b) => a.title.localeCompare(b.title));
      } else if (sortBy === 'author') {
        this.posts = this.posts.sort((a, b) =>
          a.author_name.localeCompare(b.author_name)
        );
      }
    }
  }
}
