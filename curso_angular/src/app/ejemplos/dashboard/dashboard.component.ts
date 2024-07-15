import { Component } from '@angular/core';
import { HomeComponent } from 'src/app/main';
import { DemosComponent } from '../demos/demos.component';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { NotificationComponent } from "../../main/notification/notification.component";
import { CommonModule } from '@angular/common';
import { CalculadoraComponent } from 'src/app/calculadora/calculadora.component';
import { FormularioComponent } from '../formulario/formulario.component';
import { AjaxWaitComponent } from "../../main/ajax-wait";
import { ContactosComponent } from 'src/app/contactos/contactos.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [DemosComponent, HomeComponent, GraficoSvgComponent, NotificationComponent, CommonModule, AjaxWaitComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  menu = [
    {texto: 'contactos', icono: '', componente: ContactosComponent}, //Contactos
    {texto: 'formulario', icono: '', componente: FormularioComponent}, //Demo
    {texto: 'calculadora', icono: '', componente: CalculadoraComponent}, //Calculadora
    {texto: 'inicio', icono: '', componente: HomeComponent}, //Home
    {texto: 'demos', icono: '', componente: DemosComponent}, //Demo
    {texto: 'grafico', icono: '', componente: GraficoSvgComponent}, //Gráfico
  ]
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  actual: any = this.menu[0].componente

  seleccionar(indice: number) {
    this.actual = this.menu[indice].componente
  }
}
