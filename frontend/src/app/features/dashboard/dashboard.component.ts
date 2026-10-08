import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../core/auth.service';
import { environment } from '../../../environments/environment';

interface Estudiante { id: number; nombre: string; apellido: string; correoElectronico: string; }

@Component({
  standalone: true,
  template: `
    <main>
      <h1>Sistema de matriculación</h1>
      <button (click)="logout()">Cerrar sesión</button>
      <h2>Estudiantes</h2>
      @for (estudiante of estudiantes; track estudiante.id) {
        <p>{{ estudiante.nombre }} {{ estudiante.apellido }} - {{ estudiante.correoElectronico }}</p>
      }
    </main>
  `
})
export class DashboardComponent {
  private readonly http = inject(HttpClient);
  private readonly auth = inject(AuthService);
  estudiantes: Estudiante[] = [];

  constructor() {
    this.http.get<Estudiante[]>(`${environment.apiUrl}/estudiantes`)
      .subscribe(data => this.estudiantes = data);
  }

  logout(): void {
    this.auth.logout();
    location.assign('/login');
  }
}
