import { Component } from '@angular/core';
import { ActoresListComponent } from "../../actores/componente.component";
import { PeliculasListComponent } from "../../peliculas/componente.component";
import { CategoriasListComponent } from "../../categorias/componente.component";
import { HeaderComponent } from "../header/header.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ActoresListComponent, HeaderComponent, PeliculasListComponent,CategoriasListComponent,],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  title: string = 'World';

}
