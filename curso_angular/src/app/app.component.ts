import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import { LoggerService, MyCoreModule } from '@my/core';
import { NotificationComponent, NotificationModalComponent } from './main';
import { DemosComponent } from './ejemplos/demos/demos.component';
import { HomeComponent } from "./main/home/home.component";
import { DashboardComponent } from "./ejemplos/dashboard/dashboard.component";
import { CalculadoraComponent } from './calculadora/calculadora.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SecurityModule, MyCoreModule, NotificationComponent, NotificationModalComponent, DemosComponent, HomeComponent, DashboardComponent,CalculadoraComponent,],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(log: LoggerService) {
    log.error('Es un error')
    log.warn('Es un warn')
    log.info('Es un info')
    log.log('Es un log')
  }
}
