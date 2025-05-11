import { Component } from '@angular/core';
import {NavLayoutComponent} from '../../../../layouts/nav-layout/nav-layout.component';
import {GridLayoutComponent} from '../../../../layouts/grid-layout/grid-layout.component';
import {TopicCardComponent} from '../../components/topic-card/topic-card.component';
import {NgForOf} from '@angular/common';
import {Topic} from '../../interfaces/Topic.interface';
import {TopicsService} from '../../services/topics.service';
import {take} from 'rxjs';
import {SessionService} from '../../../auth/services/session.service';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [
    NavLayoutComponent,
    GridLayoutComponent,
    TopicCardComponent,
    NgForOf
  ],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent {
  allTopics: Topic[] = [];

  constructor(
    private topicsService: TopicsService,
    public  sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.topicsService.getAllTopics()
      .pipe(take(1))
      .subscribe(topics => (this.allTopics = topics));
  }
  onTopicSubscriptionChanged(): void {
  }
}
