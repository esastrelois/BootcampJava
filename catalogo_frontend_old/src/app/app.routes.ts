import { Routes } from '@angular/router';
import { HomeComponent, PageNotFoundComponent } from './main';
import { ActoresListComponent } from './actores';

export const routes: Routes = [
  { path: '', pathMatch: 'full', component: HomeComponent },
  { path: 'inicio', component: HomeComponent },
  { path: 'actores/v1', component: ActoresListComponent},


  { path: '**', component: PageNotFoundComponent },
];