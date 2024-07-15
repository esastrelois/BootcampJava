import { Directive, ElementRef, forwardRef } from '@angular/core';
import { ValidatorFn, AbstractControl, NG_VALIDATORS, Validator, ValidationErrors } from '@angular/forms';

export function nifnieValidator(): ValidatorFn {
  // eslint-disable-next-line @typescript-eslint/consistent-indexed-object-style, @typescript-eslint/no-explicit-any
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (!control.value) { return null; }
    const err = { nifnie: { invalidFormat: true, invalidChar: true, message: 'NIF o NIE invalido' } };
    if (/^((\d{1,8})|([X-Z]\d{7}))[TRWAGMYFPDXBNJZSQVHLCKE]$/.test(control.value.toUpperCase())) {
      // eslint-disable-next-line @typescript-eslint/consistent-indexed-object-style
      const charsValue: {[index: string]: string} = { X: '0', Y: '1', Z: '2', };
      const numberValue = +((control.value as string).slice(0, -1).replace(/[X,Y,Z]/g, char => charsValue[char]));
      err.nifnie.invalidFormat = false;
      return control.value.toUpperCase().endsWith('TRWAGMYFPDXBNJZSQVHLCKE'.charAt(numberValue % 23)) ? null : err;
    } else { return err; }
  };
}
@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[nifnie][formControlName],[nifnie][formControl],[nifnie][ngModel]',
  standalone: true,
  providers: [{ provide: NG_VALIDATORS, useExisting: NIFNIEValidator, multi: true }]
})
export class NIFNIEValidator implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    return nifnieValidator()(control);
  }
}

export function uppercaseValidator(): ValidatorFn {
  // eslint-disable-next-line @typescript-eslint/consistent-indexed-object-style, @typescript-eslint/no-explicit-any
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (!control.value) { return null; }
    return control.value === control.value.toUpperCase() ? null : { uppercase: 'Tiene que estar en mayúsculas' }
  };
}
@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[uppercase][formControlName],[uppercase][formControl],[uppercase][ngModel]',
  standalone: true,
  providers: [{ provide: NG_VALIDATORS, useExisting: UppercaseValidator, multi: true }]
})
export class UppercaseValidator implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    return uppercaseValidator()(control);
  }
}

@Directive({
  // eslint-disable-next-line @angular-eslint/directive-selector
  selector: '[type][formControlName],[type][formControl],[type][ngModel]',
  standalone: true,
  providers: [
      { provide: NG_VALIDATORS, useExisting: forwardRef(() => TypeValidator), multi: true }
  ]
})
export class TypeValidator implements Validator {
  constructor(private elem: ElementRef) { }
  validate(control: AbstractControl): ValidationErrors | null {
      const valor = control.value;
      if (valor) {
        const dom = this.elem.nativeElement;
        if (dom.validity) { // dom.checkValidity();
          return (dom.validity.typeMismatch || dom.validity.stepMismatch) ? { 'type': dom.validationMessage } : null;
        }
      }
      return null;
  }
}