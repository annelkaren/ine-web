import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Documento } from 'src/app/shared/api-objects/documento';
import { ErrorResponse } from 'src/app/shared/api-objects/error-response';
import { DocumentoService } from 'src/app/shared/services/documento-service';
import { ToastService } from 'src/app/shared/services/toast-service';
import { displayFieldCss, getErrorMessage } from 'src/app/shared/utility-functions';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  @ViewChild('fileUpload') fileUpload: any;
  public isVisible: boolean = false;
  public formControl: FormGroup = new FormGroup({});
  private documento: Documento = new Documento(undefined, 0, "", "", "1", false);
  private file: any;
  public disabledButton: boolean = true;

  constructor(
    private ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private documentoService: DocumentoService,
    private toastService: ToastService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.formControl = this.formBuilder.group({
      clave: ["", [Validators.required, Validators.minLength(3)]]
    });

    this.formControl.valueChanges.subscribe(data => {
      this.documento.clave = data.clave;
    });
  }

  public addDocument(): void {
    this.isVisible = true;
  }

  public cancel(): void {
    this.isVisible = false;
  }

  public onSelect(event: any) {
    this.disabledButton = false;
    this.file = event.files[0];
  }

  public removeFile() {
    this.fileUpload.clear();
    this.disabledButton = true;
    this.file = undefined;
  }

  public save() {
    if (this.formControl.valid && this.file) {
      this.documentoService.saveFile(this.documento.clave, this.file)
        .subscribe(response => {
          this.ref.close("ok");
          this.toastService.showSuccessToast("El registro se ha guardado correctamente.");
        },
          (errorResponse: ErrorResponse) => {
            this.toastService.showErrorToast(getErrorMessage(errorResponse.error));
          });
    } else {
      this.toastService.showErrorToast("Complete los campos faltantes");
    }
  }

  public displayFieldCss(field: string) {
    return displayFieldCss(this.formControl, field);
  }

  public goToForm(): void {
    this.ref.close();
    this.router.navigate(['/form/', 0]);
  }
}
