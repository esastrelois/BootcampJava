import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import { LoggerService, MyCoreModule } from '@my/core';
import { NotificationComponent, NotificationModalComponent } from './main';
import { DemosComponent } from './demos/demos.component';
import { HomeComponent } from "./main/home/home.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SecurityModule, MyCoreModule, NotificationComponent, NotificationModalComponent, DemosComponent, HomeComponent],
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
