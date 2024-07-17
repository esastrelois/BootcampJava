import { NgModule } from '@angular/core';
import { PELICULAS_COMPONENTES, PeliculasAddComponent, PeliculasEditComponent, PeliculasListComponent, PeliculasViewComponent } from './componente.component';
import { RouterModule, Routes } from '@angular/router';
import { InRoleCanActivate } from '../security';

export const routes: Routes = [
  { path: 'v1', component: PeliculasListComponent },
  { path: 'v1', component: PeliculasAddComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: 'v1/:id', component: PeliculasEditComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: 'v1/:id', component: PeliculasViewComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ }
];

@NgModule({
  declarations: [],
  imports: [ PELICULAS_COMPONENTES, RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class PeliculasModule { }