import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AjaxWaitComponent, HeaderComponent, NotificationComponent, NotificationModalComponent } from './main';
import { NavigationService } from './common-services';
import { ActoresListComponent } from "./actores/componente.component";
import { Header } from 'primeng/api';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NotificationComponent, NotificationModalComponent, AjaxWaitComponent, ActoresListComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars, @typescript-eslint/no-empty-function
  constructor(nav: NavigationService) { }
}