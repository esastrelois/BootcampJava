import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, NIFNIEValidator, TypeValidator, UppercaseValidator } from '@my/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { Observable } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { NotificationService, NotificationType } from 'src/app/common-services';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-formulario',
  standalone: true,
  imports: [CommonModule, FormsModule, ErrorMessagePipe, NIFNIEValidator, UppercaseValidator, TypeValidator],
  templateUrl: './formulario.component.html',
  styleUrl: './formulario.component.css'
})
export class FormularioComponent {
  modo: 'add' | 'edit' = 'add'
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  elemento: any = { id: 0, nombre: 'Pepito', apellidos: 'Grillo', correo: 'pgrillo@example.com', edad: 99, fecha: '2024/07/15', conflictivo: true }

  add(){
    this.elemento = {}
    this.modo = 'add'
  }

  edit(key: number){
    this.elemento = { id: key, nombre: 'Pepito', apellidos: 'Grillo', correo: 'pgrillo@example.com', edad: 99, fecha: '2024-07-15', conflictivo: true }
    this.modo = 'edit'
  }

  cancel(){
    //
  }

  send(){
    switch(this.modo){
      case 'add':
        window.alert(`POST: ${JSON.stringify(this.elemento)}`)
        this.cancel()
        break;
      case 'edit':
        window.alert(`PUT: ${JSON.stringify(this.elemento)}`)
        this.cancel()
        break;
    }
  }


}
 