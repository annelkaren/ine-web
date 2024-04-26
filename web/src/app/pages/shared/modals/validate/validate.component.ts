import { Component } from '@angular/core';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { ToastService } from 'src/app/shared/services/toast-service';

@Component({
  selector: 'app-validate',
  templateUrl: './validate.component.html',
  styleUrls: ['./validate.component.scss']
})
export class ValidateComponent {

  public password: string;
  public internalPassword = btoa('Password0');

  constructor(
    private ref: DynamicDialogRef,
    private toastService: ToastService
  ) { }

  public close(): void {
    this.ref.close();
  }

  public validar(): void {
    if (btoa(this.password) == this.internalPassword) {
      this.ref.close("ok");
    } else{
      this.toastService.showErrorToast("Contraseña inválida");
    }
  }
}
