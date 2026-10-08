import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  standalone: true,
  imports: [FormsModule],
  template: `
    <main>
      <h1>Iniciar sesión</h1>
      <form (ngSubmit)="login()">
        <input name="username" [(ngModel)]="username" placeholder="Usuario" required>
        <input name="password" [(ngModel)]="password" type="password" placeholder="Contraseña" required>
        <button type="submit">Ingresar</button>
        @if (error) { <p>{{ error }}</p> }
      </form>
    </main>
  `
})
export class LoginComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  username = '';
  password = '';
  error = '';

  login(): void {
    this.error = '';
    this.auth.login(this.username, this.password).subscribe({
      next: () => this.router.navigateByUrl('/'),
      error: () => this.error = 'Usuario o contraseña inválidos'
    });
  }
}
