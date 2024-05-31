import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DynamicDialogRef } from 'primeng/dynamicdialog';
import { Formulario } from '../../../shared/api-objects/formulario';
import { Documento } from '../../../shared/api-objects/documento';
import { ToastService } from '../../../shared/services/toast-service';
import { ModalService } from '../../../shared/services/modal-service';
import { FormularioService } from '../../../shared/services/formulario-service';
import { DocumentoService } from '../../../shared/services/documento-service';
import { ErrorResponse } from '../../../shared/api-objects/error-response';
import { getErrorMessage } from '../../../shared/utility-functions';

@Component({
  selector: 'app-tables',
  templateUrl: './tables.component.html'
})
export class TablesComponent implements OnInit {

  public id: number = 0;
  public formControl: FormGroup = new FormGroup({});
  public formulario: Formulario = new Formulario(0, 0, "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", 0);
  public documento: Documento = new Documento(0, 0, "", "", "", false, 0);
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
        this.toastService.showErrorToast("Igrese votos");
      } else {
        if (this.documento.id == 0) {//nuevo voz
          if(this.formulario.col4) {//si tiene clave
            this.saveForm();
          } else { //No tiene
            this.toastService.showErrorToast("Agrege una clave");
          }
        } else if(this.documento.estatus == "1"){ //nuevo documento
          this.saveForm();
        } else {//valida
          if (this.validateForm()) {
            this.updateForm();
          } else {
            if (this.documento.counter < 2) {
              this.formulario.counter = this.documento.counter + 1; 
              this.updateForm();
            } else {
              this.formulario.counter = 5; 
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
    counter += this.isValid(this.formulario.pt, this.formulario2.pt);
    counter += this.isValid(this.formulario.pv, this.formulario2.pv);
    counter += this.isValid(this.formulario.mc, this.formulario2.mc);
    counter += this.isValid(this.formulario.psi, this.formulario2.psi);
    counter += this.isValid(this.formulario.morena, this.formulario2.morena);
    counter += this.isValid(this.formulario.alianza, this.formulario2.alianza);
    counter += this.isValid(this.formulario.fm, this.formulario2.fm);
    counter += this.isValid(this.formulario.col1, this.formulario2.col1);
    counter += this.isValid(this.formulario.col2, this.formulario2.col2);
    counter += this.isValid(this.formulario.col3, this.formulario2.col3);
    counter += this.isValid(this.formulario.nulos, this.formulario2.nulos);
    counter += this.isValid(this.formulario.total, this.formulario2.total);
    return counter == 15;
  }

  private isValid(field1: string, field2: string): number {
    return (parseInt(field1) == parseInt(field2)) ? 1 : 0;
  }

  public createForm(): void {
    this.formControl = this.formBuilder.group({
      col4: [""],
      pan: [0, [Validators.required, Validators.minLength(1)]],
      pri: [0, [Validators.required, Validators.minLength(1)]],
      prd: [0, [Validators.required, Validators.minLength(1)]],
      pt: [0, [Validators.required, Validators.minLength(1)]],
      pv: [0, [Validators.required, Validators.minLength(1)]],
      mc: [0, [Validators.required, Validators.minLength(1)]],
      psi: [0, [Validators.required, Validators.minLength(1)]],
      morena: [0, [Validators.required, Validators.minLength(1)]],
      alianza: [0, [Validators.required, Validators.minLength(1)]],
      fm: [0, [Validators.required, Validators.minLength(1)]],
      col1: [0, [Validators.required, Validators.minLength(1)]],
      col2: [0, [Validators.required, Validators.minLength(1)]],
      col3: [0, [Validators.required, Validators.minLength(1)]],
      nulos: [0, [Validators.required, Validators.minLength(1)]],
      total: [{ value: 0, disabled: true }, [Validators.required, Validators.minLength(1)]],
    });
  }

  private onValueChanges(): void {
    this.formControl.valueChanges.subscribe(data => {
      this.formulario.pan = data.pan;
      this.formulario.pri = data.pri;
      this.formulario.prd = data.prd;
      this.formulario.pt = data.pt;
      this.formulario.pv = data.pv;
      this.formulario.mc = data.mc;
      this.formulario.psi = data.psi;
      this.formulario.morena = data.morena;
      this.formulario.alianza = data.alianza;
      this.formulario.fm = data.fm;
      this.formulario.col1 = data.col1;
      this.formulario.col2 = data.col2;
      this.formulario.col3 = data.col3;
      this.formulario.nulos = data.nulos;
      this.formulario.total = data.total;
      this.formulario.col4 = data.col4;
      let total = data.pan + data.pri + data.prd + data.pt + data.pv + data.mc + data.psi + data.morena + data.alianza;
      total += data.fm + data.col1 + data.col2 + data.col3 + data.nulos;
      this.formControl.controls['total'].setValue(total);
      this.formulario.total = total;
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
