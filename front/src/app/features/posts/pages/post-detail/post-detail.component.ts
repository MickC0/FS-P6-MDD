import { Component } from '@angular/core';
import {Comment} from '../../interfaces/Comment.interface';
import {Post} from '../../interfaces/Post.interface';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {PostsService} from '../../services/posts.service';
import {CommentsService} from '../../services/comments.service';

import {MatButtonModule, MatIconButton} from '@angular/material/button';

import {MatInput} from '@angular/material/input';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {NavLayoutComponent} from '../../../../layouts/nav-layout/nav-layout.component';
import {BackArrowComponent} from '../../../../components/back-arrow/back-arrow.component';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-post-detail',
  imports: [
    MatIconModule,
    MatIconButton,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatInput,
    MatSelectModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatInput,
    NgForOf,
    DatePipe,
    NavLayoutComponent,
    BackArrowComponent,
    NgIf
  ],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.scss'
})
export class PostDetailComponent {
  post!: Post;
  comments!: Comment[];

  // --- FORM CONTROLS ---
  commentForm = new FormGroup({
    message: new FormControl('', [
      Validators.required,
      Validators.maxLength(500),
    ]),
  });

  constructor(
    private route: ActivatedRoute,
    private postsService: PostsService,
    private commentsService: CommentsService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.postsService.getPostDetail(id).subscribe({
      next: (post) => (this.post = post),
    });

    this.postsService.getComments(id).subscribe({
      next: (comments: Comment[]) => (this.comments = comments),
    });
  }

  // --- SUBMIT COMMENT ---
  onSubmit() {
    let temp = this.commentForm.value;
    const commentRequest = temp as { message: string };

    this.commentsService
      .createComment(this.post.id, commentRequest.message)
      .subscribe({
        next: (comment) => {
          this.postsService.getComments(this.post.id).subscribe({
            next: (comments: Comment[]) => (this.comments = comments),
          });
          this.commentForm.reset();
        },
      });
  }
}
