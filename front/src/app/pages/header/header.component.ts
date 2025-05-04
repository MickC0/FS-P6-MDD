import { Component }        from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { BreakpointObserver, Breakpoints, LayoutModule } from '@angular/cdk/layout';
import { filter, map }      from 'rxjs/operators';
import { Observable }       from 'rxjs';

import { RouterModule }     from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule }  from '@angular/material/button';
import { MatIconModule }    from '@angular/material/icon';
import { MatMenuModule }    from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';

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
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  isHandset$: Observable<boolean>;
  showNav = true;
  navLinks = [
    { path: '/articles', label: 'Articles' },
    { path: '/themes',   label: 'Thèmes'   },
  ];

  constructor(
    private breakpoint: BreakpointObserver,
    private router: Router
  ) {
    this.isHandset$ = this.breakpoint
      .observe(Breakpoints.Handset)
      .pipe(map(r => r.matches));

    this.router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe((e: NavigationEnd) => {
        const url = e.urlAfterRedirects.split('?')[0];
        this.showNav = !['/', '/login', '/register'].includes(url);
      });
  }

  logout() {
    // TODO: AuthService.logout()
    this.router.navigate(['/login']);
  }
}
