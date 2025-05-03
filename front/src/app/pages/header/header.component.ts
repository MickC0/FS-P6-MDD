import { Component }        from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { filter, map }      from 'rxjs/operators';
import { Observable }       from 'rxjs';

// Modules / directives pour standalone component
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { RouterModule }     from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule }  from '@angular/material/button';
import { MatIconModule }    from '@angular/material/icon';
import { MatMenuModule }    from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { LayoutModule }     from '@angular/cdk/layout';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    LayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    MatDividerModule,
    NgOptimizedImage,
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent {
  /** Observable pour détecter mobile */
  isHandset$: Observable<boolean>;

  /** masquer nav sur login/register */
  showNav = true;

  navLinks = [
    { path: '/articles', label: 'Articles' },
    { path: '/themes',   label: 'Thèmes'   },
  ];

  constructor(
    private breakpoint: BreakpointObserver,
    private router: Router,
  ) {
    // initialise l’observable après injection :
    this.isHandset$ = this.breakpoint
      .observe(Breakpoints.Handset)
      .pipe(map(res => res.matches));

    // contrôle showNav selon route
    this.router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe((e: NavigationEnd) => {
        const url = e.urlAfterRedirects;
        this.showNav = !['/login', '/register'].includes(url);
      });
  }

  logout() {
    // TODO: appel de votre service, puis :
    this.router.navigate(['/login']);
  }
}
