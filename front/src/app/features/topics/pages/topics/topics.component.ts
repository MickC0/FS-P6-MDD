import { Component } from '@angular/core';
import {NavLayoutComponent} from '../../../../layouts/nav-layout/nav-layout.component';
import {GridLayoutComponent} from '../../../../layouts/grid-layout/grid-layout.component';
import {TopicCardComponent} from '../../components/topic-card/topic-card.component';
import {NgForOf} from '@angular/common';
import {Topic} from '../../interfaces/Topic.interface';
import {TopicsService} from '../../services/topics.service';

@Component({
  selector: 'app-topics',
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
  allTopics: Topic[] | null = [];

  constructor(private topicsService: TopicsService) {}

  ngOnInit(): void {
    this.topicsService.getAllTopics().subscribe({
      next: (topics) => {
        this.allTopics = topics;
      },
    });
  }
}
