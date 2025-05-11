import { Component } from '@angular/core';
import { Topic } from '../../../topics/interfaces/Topic.interface';
import { TopicsService } from '../../../topics/services/topics.service';
import { PostsService } from '../../services/posts.service';
import { Router } from '@angular/router';
import {
  FormControl,
  FormGroup,
  Validators,
  FormsModule,
  ReactiveFormsModule
} from '@angular/forms';
import { take } from 'rxjs/operators';
import { Post } from '../../interfaces/Post.interface';
import { NavLayoutComponent } from '../../../../layouts/nav-layout/nav-layout.component';
import { BackArrowComponent } from '../../../../components/back-arrow/back-arrow.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDivider } from '@angular/material/divider';
import { NgFor, NgIf } from '@angular/common';
import { SessionService } from '../../../auth/services/session.service';

@Component({
  selector: 'app-new-post',
  standalone: true,
  imports: [
    NavLayoutComponent,
    BackArrowComponent,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatInput,
    MatSelectModule,
    MatButtonModule,
    MatDivider,
    NgFor,
    NgIf,
  ],
  templateUrl: './new-post.component.html',
  styleUrl: './new-post.component.scss'
})
export class NewPostComponent {
  allTopics: Topic[] = [];
  showCreateTopicForm = false;
  errorTopicMessage: string | null = null;

  public newPostForm = new FormGroup({
    topic:   new FormControl<number | null>(null, [Validators.required]),
    title:   new FormControl<string>('',      [Validators.required]),
    content: new FormControl<string>('',      [Validators.required]),
  });

  public createTopicForm = new FormGroup({
    name:        new FormControl<string>('', [Validators.required, Validators.maxLength(50)]),
    description: new FormControl<string>('', [Validators.required, Validators.maxLength(200)]),
  });

  constructor(
    private topicsService: TopicsService,
    private postService: PostsService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.reloadTopics();
  }

  private reloadTopics() {
    this.topicsService.getAllTopics()
      .pipe(take(1))
      .subscribe(topics => this.allTopics = topics);
  }

  onTopicSelection(selectedId: number) {
    if (selectedId === -1) {
      // Affiche le mini-formulaire
      this.showCreateTopicForm = true;
      this.errorTopicMessage   = null;
      this.createTopicForm.reset();
    } else {
      // Cache le mini-form si on choisit un topic normal
      this.showCreateTopicForm = false;
      this.errorTopicMessage   = null;
    }
  }

  submitNewTopic() {
    if (this.createTopicForm.invalid) return;

    const dto: Topic = {
      id: 0,
      name:        this.createTopicForm.value.name!.trim(),
      description: this.createTopicForm.value.description!.trim()
    };

    this.topicsService.createTopic(dto)
      .pipe(take(1))
      .subscribe({
        next: newTopic => {
          this.allTopics = [...this.allTopics, newTopic];
          this.newPostForm.get('topic')!.setValue(newTopic.id);
          this.showCreateTopicForm = false;
        },
        error: err => {
          const msg = err.error?.err || err.message;
          if (err.status === 400 && err.error?.error) {
            this.errorTopicMessage = err.error.error;
            this.nameControl.setErrors({ duplicate: true });
          } else {
            this.errorTopicMessage = 'Erreur lors de la création du thème';
          }
        }
      });
  }

  cancelNewTopic() {
    this.showCreateTopicForm = false;
    this.newPostForm.get('topic')!.setValue(null);
  }
  get nameControl(): FormControl {
    return this.createTopicForm.get('name') as FormControl;
  }

  get descriptionControl(): FormControl {
    return this.createTopicForm.get('description') as FormControl;
  }

  public onSubmit(): void {
    if (this.newPostForm.invalid) {
      return;
    }

    const currentUser = this.sessionService.user!;
    const { topic, title, content } = this.newPostForm.value!;
    if (topic == null || title == null || content == null) return;

    const postPayload: Post = {
      id: 0,
      title,
      content,
      author_id:   currentUser.id,
      author_name: currentUser.name,
      topic_id:    topic,
      topic_name:  '',
      createdAt:   ''
    };

    this.postService
      .createPost(postPayload)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.newPostForm.reset();
          this.router.navigate(['feed']);
        },
        error: err => console.error('Échec création post', err)
      });
  }
}
