import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InRoleCanActivate } from '../security';
import { ActoresListComponent, ActoresAddComponent, ActoresEditComponent, ActoresViewComponent } from './componente.component';

export const routes: Routes = [
  { path: '', component: ActoresListComponent },
  { path: 'actores/v1', component: ActoresAddComponent, canActivate: [ InRoleCanActivate('Actores')] },
  { path: 'actores/v1/:id', component: ActoresEditComponent, canActivate: [ InRoleCanActivate('Actores')] },
  { path: 'actores/v1/:id', component: ActoresViewComponent, canActivate: [ InRoleCanActivate('Actores')] }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes),
    ActoresListComponent,
    ActoresAddComponent,
    ActoresEditComponent,
    ActoresViewComponent
  ],
  exports: [ RouterModule ]
})
export class ActoresModule { }