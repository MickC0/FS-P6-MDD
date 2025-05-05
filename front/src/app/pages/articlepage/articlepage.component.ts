import { Component }                 from '@angular/core';
import { CommonModule }              from '@angular/common';
import { Router }                    from '@angular/router';
import { MatButtonModule }           from '@angular/material/button';
import { MatIconModule }             from '@angular/material/icon';
import { MatFormFieldModule }        from '@angular/material/form-field';
import { MatSelectModule }           from '@angular/material/select';
import { MatCardModule }             from '@angular/material/card';
import { FormsModule }               from '@angular/forms';

interface Article {
  id: number;
  title: string;
  date: Date;
  author: string;
  content: string;
}

@Component({
  selector: 'app-articlepage',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCardModule,
  ],
  templateUrl: './articlepage.component.html',
  styleUrls: ['./articlepage.component.scss']
})
export class ArticlepageComponent {
  articles: Article[] = [
    { id: 1, title: 'Titre de l’article', date: new Date(), author: 'Auteur', content: 'Content: lorem ipsum…' },
    { id: 2, title: 'Titre de l’article', date: new Date(), author: 'Auteur', content: 'Content: lorem ipsum…' },
    { id: 3, title: 'Titre de l’article', date: new Date(), author: 'Auteur', content: 'Content: lorem ipsum…' },
    { id: 4, title: 'Titre de l’article', date: new Date(), author: 'Auteur', content: 'Content: lorem ipsum…' },
  ];

  sortOptions = [
    { value: 'dateDesc', viewValue: 'Date ↓' },
    { value: 'dateAsc',  viewValue: 'Date ↑' },
    { value: 'title',    viewValue: 'Titre'   },
  ];
  selectedSort = 'dateDesc';
  selectedArticleId: number|null = null;

  constructor(private router: Router) {}

  createArticle() {
    this.router.navigate(['/articles/create']);
  }

  onSortChange(sort: string) {
    this.selectedSort = sort;
    console.log('Trier par', sort);
  }

  selectArticle(id: number) {
    this.selectedArticleId = id;
  }

  goToArticle(id: number) {
    this.router.navigate(['/articles', id]);
  }
}
