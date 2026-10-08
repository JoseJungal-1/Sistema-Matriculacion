import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

interface AuthResponse { token: string; tokenType: string; expiresIn: number; }

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly api = environment.apiUrl;

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.api}/auth/login`, { username, password })
      .pipe(tap(response => localStorage.setItem('access_token', response.token)));
  }

  logout(): void { localStorage.removeItem('access_token'); }
  isAuthenticated(): boolean { return Boolean(localStorage.getItem('access_token')); }
}
