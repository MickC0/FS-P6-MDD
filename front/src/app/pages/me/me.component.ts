import { Component, OnInit }              from '@angular/core';
import { CommonModule }                    from '@angular/common';
import { FormsModule }                     from '@angular/forms';
import { MatFormFieldModule }              from '@angular/material/form-field';
import { MatInputModule }                  from '@angular/material/input';
import { MatIconModule }                   from '@angular/material/icon';
import { MatButtonModule }                 from '@angular/material/button';
import { MatDividerModule }                from '@angular/material/divider';
import { MatCardModule }                   from '@angular/material/card';

@Component({
  selector: 'app-me',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule,
    MatCardModule,
  ],
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss'],
})
export class MeComponent implements OnInit {
  hide = true;
  profile = { username: '', email: '', password: '' };
  subscriptions: { title: string; description: string }[] = [];

  ngOnInit() {
    // TODO: remplacer par appel à vos services
    this.profile = {
      username: 'Username',
      email:    'email@email.fr',
      password: ''
    };
    this.subscriptions = [
      {
        title: 'Titre du thème',
        description: `Description : Lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard...`
      },
      {
        title: 'Titre du thème',
        description: `Description : Lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard...`
      }
    ];
  }

  onSave() {
    // TODO: appeler votre service de mise à jour de profil
    console.log('Profil sauvegardé', this.profile);
  }

  unsubscribe(i: number) {
    // TODO: appeler votre service d’abonnement
    console.log('Désabonnement de', this.subscriptions[i].title);
    this.subscriptions.splice(i, 1);
  }
}
