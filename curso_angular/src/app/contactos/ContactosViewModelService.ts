import { HttpContextToken, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';

//Tipo de datos para controlar la transición de estados
export type ModoCRUD = 'list' | 'add' | 'edit' | 'view' | 'delete';

//Token para activar la seguridad
export const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

@Injectable({
  providedIn: 'root'
})
export class ContactosViewModelService {
  protected modo: ModoCRUD = 'list'; 
  // eslint-disable-next-line @typescript-eslint/array-type, @typescript-eslint/no-explicit-any
  protected listado: Array<any> = []; 
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  protected elemento: any = {}; 
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  protected idOriginal: any = null;

  constructor(protected notify: NotificationService, 
              protected out: LoggerService, 
              protected dao: ContactosDAOService) { }

  public get Modo(): ModoCRUD { return this.modo; } 
  // eslint-disable-next-line @typescript-eslint/array-type, @typescript-eslint/no-explicit-any
  public get Listado(): Array<any> { return this.listado; } 
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public get Elemento(): any { return this.elemento; }


  //Obtener el listado a mostrar
  public list(): void { 
    this.dao.query().subscribe({ 
      next: data => { 
        this.listado = data; 
        this.modo = 'list'; 
      }, 
      error: err => this.handleError(err) 
    }); 
  }


  //Operaciones con la entidad
  public add(): void { 
    this.elemento = {}; 
    this.modo = 'add'; 
  } 
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public edit(key: any): void { 
    this.dao.get(key).subscribe({ 
      next: data => { 
        this.elemento = data; 
        this.idOriginal = key; 
        this.modo = 'edit'; 
      }, 
      error: err => this.handleError(err) 
    }); 
  }
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public view(key: any): void { 
    this.dao.get(key).subscribe({ 
      next: data => { 
        this.elemento = data; 
        this.modo = 'view'; 
      }, 
      error: err => this.handleError(err) 
    }); 
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } public delete(key: any): void { 
    if (!window.confirm('¿Seguro?')) { return; } 
    this.dao.remove(key).subscribe({ 
      next: () => this.list(), 
      error: err => this.handleError(err) 
    }); 
  }


  //Cerrar la vista de detalle o formulario
  public cancel(): void { 
    this.elemento = {}; 
    this.idOriginal = null; 
    this.list(); 
  } 
  public send(): void { 
    switch (this.modo) { 
      case 'add': 
        this.dao.add(this.elemento).subscribe({ 
          next: () => this.cancel(), 
          error: err => this.handleError(err) 
        }); 
        break; 
      case 'edit': 
        this.dao.change(this.idOriginal, this.elemento).subscribe({ 
          next: () => this.cancel(), 
          error: err => this.handleError(err) 
        }); 
        break; 
      case 'view': 
        this.cancel(); break; 
    } 
  }

  
  //Liberar la memoria cuando ya no es necesaria
  clear() { 
    this.listado = []; 
  }


  //Manipulador de errores para su notificación
  handleError(err: HttpErrorResponse) { 
    let msg = '' 
    switch (err.status) { 
      case 0: 
        msg = err.message; 
        break; 
      case 404: 
        msg = `ERROR ${err.status}: ${err.statusText}`; 
        break; 
      default: 
        msg = `ERROR ${err.status}: ${err.error?.['title'] ?? 
          err.statusText}.${err.error?.['detail'] ? ` Detalles: ${err.error['detail']}` : ''}` 
          break; 
    } 
    this.notify.add(msg) 
  }


}

//Clase base de los servicios DAO
import { HttpClient } from '@angular/common/http'; 
import { inject } from '@angular/core'; 
import { Observable } from 'rxjs'; 
import { environment } from 'src/environments/environment'; 
import { NotificationService } from '../common-services';
import { LoggerService } from '@my/core';

export abstract class RESTDAOService<T, K> { 
  protected baseUrl = environment.apiURL; 
  protected http: HttpClient = inject(HttpClient) 
  constructor(entidad: string, protected option = {}) { 
    this.baseUrl += entidad; 
  } 
  // eslint-disable-next-line @typescript-eslint/array-type
  query(): Observable<Array<T>> { 
    // eslint-disable-next-line @typescript-eslint/array-type
    return this.http.get<Array<T>>(this.baseUrl, this.option); 
  } get(id: K): Observable<T> { 
    return this.http.get<T>(`${this.baseUrl}/${id}`, this.option); 
  } add(item: T): Observable<T> { 
    return this.http.post<T>(this.baseUrl, item, this.option); 
  } change(id: K, item: T): Observable<T> { 
    return this.http.put<T>(`${this.baseUrl}/${id}`, item, this.option); 
  } remove(id: K): Observable<T> { 
    return this.http.delete<T>(`${this.baseUrl}/${id}`, this.option); 
  } 
}


//Servicio DAO 
@Injectable({ 
  providedIn: 'root' 
}) 
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export class ContactosDAOService extends RESTDAOService<any, any> { 
  constructor() { 
    super('contactos'); 
  } 
}
