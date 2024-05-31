import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DynamicDialogConfig, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Documento } from 'src/app/shared/api-objects/documento';
import { ErrorResponse } from 'src/app/shared/api-objects/error-response';
import { DocumentoService } from 'src/app/shared/services/documento-service';
import { ToastService } from 'src/app/shared/services/toast-service';
import { displayFieldCss, getErrorMessage } from 'src/app/shared/utility-functions';
import { SeccionService } from '../../../../shared/services/seccion-service';
import { Seccion } from '../../../../shared/api-objects/seccion';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss']
})
export class AddComponent implements OnInit {

  @ViewChild('fileUpload') fileUpload: any;
  public isVisible: boolean = false;
  public formControl: FormGroup = new FormGroup({});
  private documento: Documento = new Documento(undefined, 0, "", "", "1", false, 0);
  private file: any;
  public disabledButton: boolean = true;
  public array: Seccion[] = [];
  public selectedCity: Seccion | undefined;

  constructor(
    private ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private documentoService: DocumentoService,
    private toastService: ToastService,
    private formBuilder: FormBuilder,
    private router: Router,
    private seccionService: SeccionService
  ) { }

  ngOnInit(): void {

    this.seccionService.getAll().then(result => {
      if (result) {
        this.array = result;
      }
    });

    this.formControl = this.formBuilder.group({
      clave: [0, [Validators.required, Validators.min(1)]],
      casilla: [0, [Validators.required, Validators.min(1)]],
      tipo: ["", [Validators.required]]
    });

    this.formControl.valueChanges.subscribe(data => {
      this.documento.clave = data.clave + data.casilla + data.tipo;
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
