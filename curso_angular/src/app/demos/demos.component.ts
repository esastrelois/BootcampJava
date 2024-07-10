import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService, NotificationType } from '../common-services/notification.service';
import { Unsubscribable } from 'rxjs';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css'
})
export class DemosComponent implements OnInit, OnDestroy {
  //Atributo que almacena al suscriptor para poder cancelar la suscripción al destruir el componente
  private suscriptor: Unsubscribable | undefined;

  constructor(public vm: NotificationService) { }

  //Se crea la suscripción y se indica el tratamiento de las nuevas notificaciones
  ngOnInit(): void { 
    this.suscriptor = this.vm.Notificacion.subscribe(n => { 
      if (n.Type !== NotificationType.error) { 
        return; 
      } 
      window.alert(`Suscripcion: ${n.Message}`); 
      this.vm.remove(this.vm.Listado.length - 1); }); 
  }

  //Al destruir el componente se debe cancelar la suscripción para evitar fugas de memoria y de proceso
  ngOnDestroy(): void { 
    if (this.suscriptor) { 
      this.suscriptor.unsubscribe(); 
    } 
  }
}