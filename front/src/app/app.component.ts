import { Component } from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {filter, Observable} from 'rxjs';
import {HeaderComponent} from './pages/header/header.component';
import {AsyncPipe, NgIf} from '@angular/common';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, NgIf, AsyncPipe,],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'MDD';

  showHeader$: Observable<boolean>;

  constructor(private router: Router) {
    this.showHeader$ = this.router.events.pipe(
      filter(e => e instanceof NavigationEnd),
      map((e: NavigationEnd) => {
        const p = e.urlAfterRedirects.split('?')[0].split('#')[0];
        return p !== '/';
      })
    );
  }
}
