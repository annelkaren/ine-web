import { Component } from '@angular/core';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-validate',
  templateUrl: './validate.component.html',
  styleUrls: ['./validate.component.scss']
})
export class ValidateComponent {

  constructor(
    private ref: DynamicDialogRef
  ){}

  public close(): void {
    this.ref.close();
  }

  public aprobar(): void {
    this.ref.close("ok");
  }
}
