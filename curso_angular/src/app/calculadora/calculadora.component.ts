import { Component } from '@angular/core';

@Component({
  selector: 'app-calculadora',
  standalone: true,
  templateUrl: './calculadora.component.html',
  styleUrls: ['./calculadora.component.css']
})
export class CalculadoraComponent {
  displayValue: string = '0';
  operando1: number = 0;
  operando2: number = 0;
  operacionARealizar: string = '';
  resultado: number | string = '';

  procesaDigito(digito: string) {
    if (this.displayValue === '0') {
      this.displayValue = digito;
    } else {
      this.displayValue += digito;
    }
  }

  queOperacion(operacion: string) {
    if (operacion === 'x') {
      this.operacionARealizar = '*';
    } else {
      this.operacionARealizar = operacion;
    }
    this.operando1 = parseFloat(this.displayValue.replace(',', '.'));
    this.displayValue = '0';
  }

  realizarOperacion() {
    this.operando2 = parseFloat(this.displayValue.replace(',', '.'));
    let resultado: number;
    switch (this.operacionARealizar) {
      case '+':
        resultado = this.operando1 + this.operando2;
        break;
      case '-':
        resultado = this.operando1 - this.operando2;
        break;
      case '*':
        resultado = this.operando1 * this.operando2;
        break;
      case '/':
        resultado = this.operando1 / this.operando2;
        break;
      default:
        resultado = 0;
        break;
    }
    this.resultado = this.redondearResultado(resultado);
    this.displayValue = this.resultado.toString().replace('.', ',');
  }

  redondearResultado(resultado: number): string {
    return parseFloat(resultado.toFixed(10)).toString().replace('.', ',');
  }

  comaDecimal() {
    if (!this.displayValue.includes(',')) {
      this.displayValue += ',';
    }
  }

  borrar() {
    this.displayValue = '0';
    this.operando1 = 0;
    this.operacionARealizar = '';
  }

  resolverUnitario(operacion: string) {
    this.operando1 = parseFloat(this.displayValue.replace(',', '.'));
    let resultado: number;
    switch (operacion) {
      case 'sin':
        resultado = Math.sin(this.operando1);
        break;
      case 'cos':
        resultado = Math.cos(this.operando1);
        break;
      case 'tan':
        resultado = Math.tan(this.operando1);
        break;
      case 'sqrt':
        resultado = Math.sqrt(this.operando1);
        break;
      default:
        resultado = 0;
        break;
    }
    this.resultado = this.redondearResultado(resultado);
    this.displayValue = this.resultado.toString().replace('.', ',');
  }
}
