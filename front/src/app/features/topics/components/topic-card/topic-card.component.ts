import {Component, Input} from '@angular/core';
import {User} from '../../../user/interfaces/User.interface';
import {take} from 'rxjs';
import {MatButton} from '@angular/material/button';
import {NgIf} from '@angular/common';
import {Topic} from '../../interfaces/Topic.interface';
import {SessionService} from '../../../auth/services/session.service';
import {TopicsService} from '../../services/topics.service';

@Component({
  selector: 'app-topic-card',
  imports: [
    MatButton,
    NgIf
  ],
  templateUrl: './topic-card.component.html',
  styleUrl: './topic-card.component.scss'
})
export class TopicCardComponent {
  @Input() topic!: Topic;
  @Input() user!: User | undefined;

  constructor(
    private topicsService: TopicsService,
    private sessionService: SessionService
  ) {}

  hasSubscribed(topic: Topic): boolean | undefined {
    return this.user?.subscriptions.some((sub) => sub.id === topic.id);
  }

  ngOnInit(): void {
    this.user = this.sessionService.user;
  }

  subscribeToTopic(topic: Topic): void {
    this.topicsService
      .subscribeTopic(topic.id)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.user?.subscriptions.push(topic);
        },
      });
  }

  unsubscribeFromTopic(topic: Topic): void {
    this.topicsService
      .unsubscribeTopic(topic.id)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.user!.subscriptions = this.user!.subscriptions.filter(
            (sub) => sub.id !== topic.id
          );
        },
      });
  }
}
