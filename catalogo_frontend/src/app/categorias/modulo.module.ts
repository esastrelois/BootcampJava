import { NgModule } from '@angular/core';
import { ACTORES_COMPONENTES, CategoriasAddComponent, CategoriasEditComponent, CategoriasListComponent} from './componente.component';
import { RouterModule, Routes } from '@angular/router';
import { InRoleCanActivate } from '../security';

export const routes: Routes = [
  { path: '', component: CategoriasListComponent },
  { path: 'add', component: CategoriasAddComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
  { path: ':id/edit', component: CategoriasEditComponent/*, canActivate: [ InRoleCanActivate('Empleados')]*/ },
 ];

@NgModule({
  declarations: [],
  imports: [ ACTORES_COMPONENTES, RouterModule.forChild(routes) ],
  exports: [ RouterModule ]
})
export class CategoriasModule { }