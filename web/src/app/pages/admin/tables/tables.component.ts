import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Documento } from 'src/app/shared/api-objects/documento';
import { ErrorResponse } from 'src/app/shared/api-objects/error-response';
import { Formulario } from 'src/app/shared/api-objects/formulario';
import { DocumentoService } from 'src/app/shared/services/documento-service';
import { FormularioService } from 'src/app/shared/services/formulario-service';
import { ModalService } from 'src/app/shared/services/modal-service';
import { ToastService } from 'src/app/shared/services/toast-service';
import { getErrorMessage } from 'src/app/shared/utility-functions';

@Component({
  selector: 'app-tables',
  templateUrl: './tables.component.html'
})
export class TablesComponent implements OnInit {

  public id: number = 0;
  public formControl: FormGroup = new FormGroup({});
  public formulario: Formulario = new Formulario(0, 0, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
  public documento: Documento = new Documento(0, 0, "", "", "", false);
  public formulario2: Formulario;
  public data: boolean = false;
  @ViewChild('pdf') pdf!: ElementRef;
  @ViewChild('image') image!: ElementRef;
  @ViewChild('noImage') noImage!: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private toastService: ToastService,
    private formularioService: FormularioService,
    private documentoService: DocumentoService,
    private modalService: ModalService

  ) {
    let id: number = 0;
    this.route.params.subscribe(params => {
      id = params['id'];
    });
    if (id > 0) {
      this.formularioService.getByDocumentId(id).then(result => {
        if (result) {
          this.formulario2 = result;
        }
      });

      this.documentoService.getById(id).then(result => {
        if (result) {
          this.documento = result;
          this.getFile(this.documento.url);
        }
      });
    }
  }

  ngOnInit(): void {
    this.createForm();
    this.onValueChanges();
  }

  public cancel(): void {
    this.router.navigate(["/home"]);
  }

  public save(): void {
    if (this.formControl.valid) {
      if (this.formulario.total === "0") {
        this.toastService.showErrorToast("Igrese el total de votos");
      } else {
        if (this.documento.id == 0 || this.documento.estatus == "1") {//nuevo
          this.saveForm();
        } else {//valida
          if (this.validateForm()) {
            this.updateForm();
          } else {
            let ref: DynamicDialogRef = this.modalService.noValid();
            ref.onClose.subscribe((data) => {
              if (data === 'ok') {
                let ref: DynamicDialogRef = this.modalService.valid();
                ref.onClose.subscribe((data) => {
                  if (data === 'ok') {
                    this.updateForm();
                  }
                });
              }
            });
          }
        }
      }
    } else {
      this.toastService.showErrorToast("Complete los campos faltantes");
    }
  }

  private saveForm(): void {
    this.formulario.docId = this.documento.id;
    this.formularioService.save(this.formulario)
      .subscribe(response => {
        this.router.navigate(["/home"]);
        this.toastService.showSuccessToast("El registro se ha guardado correctamente.");
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(getErrorMessage(errorResponse.error));
        });
  }

  private updateForm(): void {
    this.formulario.docId = this.documento.id;
    this.formularioService.update(this.formulario)
      .subscribe(response => {
        this.router.navigate(["/home"]);
        this.toastService.showSuccessToast("El registro se ha guardado correctamente.");
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(getErrorMessage(errorResponse.error));
        });
  }

  private validateForm(): boolean {
    let counter = 0;
    counter += this.isValid(this.formulario.pan, this.formulario2.pan);
    counter += this.isValid(this.formulario.pri, this.formulario2.pri);
    counter += this.isValid(this.formulario.prd, this.formulario2.prd);
    counter += this.isValid(this.formulario.morena, this.formulario2.morena);
    counter += this.isValid(this.formulario.alianza, this.formulario2.alianza);
    counter += this.isValid(this.formulario.ci, this.formulario2.ci);
    counter += this.isValid(this.formulario.col1, this.formulario2.col1);
    counter += this.isValid(this.formulario.col2, this.formulario2.col2);
    counter += this.isValid(this.formulario.col3, this.formulario2.col3);
    counter += this.isValid(this.formulario.col4, this.formulario2.col4);
    counter += this.isValid(this.formulario.col5, this.formulario2.col5);
    counter += this.isValid(this.formulario.col6, this.formulario2.col6);
    counter += this.isValid(this.formulario.col7, this.formulario2.col7);
    counter += this.isValid(this.formulario.col8, this.formulario2.col8);
    counter += this.isValid(this.formulario.col9, this.formulario2.col9);
    counter += this.isValid(this.formulario.col10, this.formulario2.col10);
    counter += this.isValid(this.formulario.col11, this.formulario2.col11);
    counter += this.isValid(this.formulario.col12, this.formulario2.col12);
    counter += this.isValid(this.formulario.nulos, this.formulario2.nulos);
    counter += this.isValid(this.formulario.total, this.formulario2.total);
    return counter == 20;
  }

  private isValid(field1: string, field2: string): number {
    return (parseInt(field1) == parseInt(field2)) ? 1 : 0;
  }

  public createForm(): void {
    this.formControl = this.formBuilder.group({
      pan: [0, [Validators.required, Validators.minLength(1)]],
      pri: [0, [Validators.required, Validators.minLength(1)]],
      prd: [0, [Validators.required, Validators.minLength(1)]],
      morena: [0, [Validators.required, Validators.minLength(1)]],
      alianza: [0, [Validators.required, Validators.minLength(1)]],
      ci: [0, [Validators.required, Validators.minLength(1)]],
      col1: [0, [Validators.required, Validators.minLength(1)]],
      col2: [0, [Validators.required, Validators.minLength(1)]],
      col3: [0, [Validators.required, Validators.minLength(1)]],
      col4: [0, [Validators.required, Validators.minLength(1)]],
      col5: [0, [Validators.required, Validators.minLength(1)]],
      col6: [0, [Validators.required, Validators.minLength(1)]],
      col7: [0, [Validators.required, Validators.minLength(1)]],
      col8: [0, [Validators.required, Validators.minLength(1)]],
      col9: [0, [Validators.required, Validators.minLength(1)]],
      col10: [0, [Validators.required, Validators.minLength(1)]],
      col11: [0, [Validators.required, Validators.minLength(1)]],
      col12: [0, [Validators.required, Validators.minLength(1)]],
      nulos: [0, [Validators.required, Validators.minLength(1)]],
      total: [0, [Validators.required, Validators.minLength(1)]],
    });
  }

  private onValueChanges(): void {
    this.formControl.valueChanges.subscribe(data => {
      this.formulario.pan = data.pan;
      this.formulario.pri = data.pri;
      this.formulario.prd = data.prd;
      this.formulario.morena = data.morena;
      this.formulario.alianza = data.alianza;
      this.formulario.ci = data.ci;
      this.formulario.col1 = data.col1;
      this.formulario.col2 = data.col2;
      this.formulario.col3 = data.col3;
      this.formulario.col4 = data.col4;
      this.formulario.col5 = data.col5;
      this.formulario.col6 = data.col6;
      this.formulario.col7 = data.col7;
      this.formulario.col8 = data.col8;
      this.formulario.col9 = data.col9;
      this.formulario.col10 = data.col10;
      this.formulario.col11 = data.col11;
      this.formulario.col12 = data.col12;
      this.formulario.nulos = data.nulos;
      this.formulario.total = data.total;
    });
  }

  public getFile(filename: string): any {
    this.documentoService.getFile(filename)
      .subscribe(response => {
        if (response.name.includes("pdf")) {
          this.pdf.nativeElement.src = "data:application/pdf;base64, " + response.value;
          this.image.nativeElement.hidden = true;
        } else {
          this.pdf.nativeElement.hidden = true;
          this.image.nativeElement.src = "data:image/png;base64, " + response.value;
        }
      });
  }
}
