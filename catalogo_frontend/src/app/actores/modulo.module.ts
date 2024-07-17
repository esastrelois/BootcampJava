import { NgModule } from '@angular/core';
import { ACTORES_COMPONENTES, ActoresAddComponent, ActoresEditComponent, ActoresListComponent, ActoresViewComponent } from './componente.component';
import { RouterModule, Routes } from '@angular/router';
import { InRoleCanActivate } from '../security';

export const routes: Routes = [
  { path: 'v1', component: ActoresListComponent },
  { path: 'v1', component: ActoresAddComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: 'v1/:id', component: ActoresEditComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: 'v1/:id', component: ActoresViewComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ }
];

@NgModule({
  declarations: [],
  imports: [ ACTORES_COMPONENTES, RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class ActoresModule { }