import { FormGroup } from "@angular/forms";

export function getErrorMessage(errorCode: string): string {
    switch (errorCode) {
        case "E006":
            return "Ya existe un registro con la misma clave, intente con otra.";
        case "E007":
                return "La casilla que intenta registrar no existe.";
        case "E008":
                return "La clave ingresada no es válida, intente con otra.";
        case "E000":
        default:
            return "Ha ocurrido un error inesperado, contacte al administrador del sistema";
    }
}

export function displayFieldCss(formControl: FormGroup, field: string) {
    return {
        'is-invalid': !formControl.get(field)?.valid && formControl.get(field)?.touched
    };
}