import {Component, Input} from '@angular/core';
import {Post} from '../../interfaces/Post.interface';
import {RouterLink} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-post-card',
  standalone: true,
  imports: [
    RouterLink,
    DatePipe
  ],
  templateUrl: './post-card.component.html',
  styleUrl: './post-card.component.scss'
})
export class PostCardComponent {
  @Input() post!: Post;
}
