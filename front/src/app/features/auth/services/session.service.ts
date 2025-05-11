import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {User} from '../../user/interfaces/User.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  public user?: User;
  private isLoggedSubject = new BehaviorSubject<boolean>(!!localStorage.getItem('token'));
  public isLogged$(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: User, token?: string): void {
    this.user = user;
    if (token) {
      localStorage.setItem('token', token);
    }
    this.isLoggedSubject.next(true);
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.user = undefined;
    this.isLoggedSubject.next(false);
  }

  public getToken(): string | null {
    return localStorage.getItem('token');
  }
}
