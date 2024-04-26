import { Component } from '@angular/core';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-no-valid',
  templateUrl: './no-valid.component.html',
  styleUrls: ['./no-valid.component.scss']
})
export class NoValidComponent {

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
