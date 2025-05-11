// src/app/topics/components/topic-card.component.ts
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { take } from 'rxjs/operators';
import { MatButton } from '@angular/material/button';
import { NgIf }      from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

import { Topic }   from '../../interfaces/Topic.interface';
import { User }    from '../../../user/interfaces/User.interface';
import { TopicsService } from '../../services/topics.service';
import {SessionService} from '../../../auth/services/session.service';

@Component({
  selector: 'app-topic-card',
  standalone: true,
  imports: [ MatButton, NgIf ],
  templateUrl: './topic-card.component.html',
  styleUrl:    './topic-card.component.scss'
})
export class TopicCardComponent {
  @Input()  topic!: Topic;
  @Input()  user?: User;
  @Output() subscriptionChange = new EventEmitter<void>();
  @Input() disableUnsubscribe = false;

  constructor(
    private topicsService: TopicsService,
    private sessionService: SessionService,
    private snackBar: MatSnackBar
  ) {}

  hasSubscribed(): boolean {
    return this.user?.subscriptions.some(s => s.id === this.topic.id) ?? false;
  }

  subscribeToTopic(): void {
    if (!this.user) return;
    this.topicsService.subscribeTopic(this.topic.id)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.user!.subscriptions.push(this.topic);
          this.sessionService.logIn(this.user!);
          this.subscriptionChange.emit();
          this.snackBar.open('Abonnement réussi', 'Fermer', { duration: 3000 });
        },
        error: err => {
          console.error('Erreur abonnement', err);
          this.snackBar.open(
            'Échec de l’abonnement, veuillez réessayer.',
            'Fermer',
            { duration: 5000, panelClass: ['snackbar-error'] }
          );
        }
      });
  }

  unsubscribeFromTopic(): void {
    if (!this.user || this.disableUnsubscribe) return;
    this.topicsService.unsubscribeTopic(this.topic.id)
      .pipe(take(1))
      .subscribe({
        next: () => {
          this.user!.subscriptions =
            this.user!.subscriptions.filter(s => s.id !== this.topic.id);
          this.sessionService.logIn(this.user!);
          this.subscriptionChange.emit();
          this.snackBar.open('Désabonnement réussi', 'Fermer', { duration: 3000 });
        },
        error: err => {
          console.error('Erreur désabonnement', err);
          this.snackBar.open(
            'Échec du désabonnement, veuillez réessayer.',
            'Fermer',
            { duration: 5000, panelClass: ['snackbar-error'] }
          );
        }
      });
  }
}
