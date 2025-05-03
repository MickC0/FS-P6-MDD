import { Component } from '@angular/core';
import {NavigationEnd, Router, RouterOutlet} from '@angular/router';
import {filter} from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'MDD';
}
