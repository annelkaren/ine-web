import { Component, OnInit } from '@angular/core';
import { Documento } from '../../../shared/api-objects/documento';
import { ErrorResponse } from '../../../shared/api-objects/error-response';
import { DocumentoService } from '../../../shared/services/documento-service';
import { ToastService } from '../../../shared/services/toast-service';
import { printCasilla50, printCasilla60, printCasilla70, printCasilla80, printDoughnutChart, printLeaveReportChart, printPieChart } from '../../../../assets/js/graphics.js'
import { Counter } from '../../../shared/api-objects/counter.js';
import { ModalService } from '../../../shared/services/modal-service';
import { Router } from '@angular/router';
import { Data } from '../../../shared/api-objects/data.js';
import { DynamicDialogRef } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public documentos!: Documento[];
  public first: number = 0;
  public rows: number = 0;
  public selectedDocuments: Documento[] = [];
  public totalRecords: number = 0;
  public keyFilter: string = "";
  public nameFilter: string = "";
  public page: number = 0;
  private counterO!: Counter;
  private data!: Data;
  public c50: boolean = false;
  public c60: boolean = false;
  public c70: boolean = false;
  public c80: boolean = false;
  public stateOptions: any[] = [{ label: 'Todos', value: 1 }, { label: 'Documento', value: 2 }, { label: 'Voz', value: 3 }];
  public value: number = 1;

  constructor(
    private documentoService: DocumentoService,
    private toastService: ToastService,
    private modalService: ModalService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getAll(0);
    this.counter();
    this.graphic();
    this.casillas();
  }

  public add(): void {
    let ref: DynamicDialogRef = this.modalService.add("Agregar acta de escrutinio");
    ref.onClose.subscribe((data) => {
      if (data === 'ok') {
        this.filter();
        this.counter();
        this.selectedDocuments = [];
      }
    });
  }

  public filter(): void {
    if (this.keyFilter) {
      this.getAllByFilters(0);
    } else {
      this.getAll(0);
    }
  }

  public getAll(page: number): void {
    this.page = page;
    if (!this.value) {
      this.value = 1;
    }
    this.documentoService.getAll(this.value, page)
      .subscribe(response => {
        this.documentos = response.content;
        this.rows = response.pageable.pageSize;
        this.totalRecords = response.totalElements;
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(errorResponse.error);
        });
  }

  public editar(document: any): void {
    if (!document.isVoice && document.estatus != '4') {
      this.router.navigate(['/form/', document.id]);
    }
  }

  public counter(): void {
    this.documentoService.counter()
      .subscribe(response => {
        this.counterO = response;
        printDoughnutChart(this.counterO.noIniciado, this.counterO.validado);
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(errorResponse.error);
        });
  }

  public graphic(): void {
    this.documentoService.graphic()
      .subscribe(response => {
        this.data = response;
        printLeaveReportChart(this.data.pan, this.data.pri, this.data.prd, this.data.pt, this.data.pv, this.data.mc, this.data.psi, this.data.morena, this.data.alianza, this.data.fm, this.data.col1, this.data.col2);
        printPieChart(this.data.pan, this.data.pri, this.data.prd, this.data.pt, this.data.pv, this.data.mc, this.data.psi, this.data.morena, this.data.alianza, this.data.fm, this.data.col1, this.data.col2);
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(errorResponse.error);
        });
  }

  public casillas(): void {
    this.documentoService.casillas()
      .subscribe(response => {
        console.log(response);
        this.c50 = response.casilla50.terminado;
        this.c60 = response.casilla60.terminado;
        this.c70 = response.casilla70.terminado;
        this.c80 = response.casilla80.terminado;
        if (response.casilla50.data) {
          printCasilla50(response.casilla50.data.pan, response.casilla50.data.pri, response.casilla50.data.prd, response.casilla50.data.pt, response.casilla50.data.pv, response.casilla50.data.mc, response.casilla50.data.psi, response.casilla50.data.morena, response.casilla50.data.alianza, response.casilla50.data.fm, response.casilla50.data.col1, response.casilla50.data.col2);
        } else {
          printCasilla50(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        if (response.casilla60.data) {
          printCasilla60(response.casilla60.data.pan, response.casilla60.data.pri, response.casilla60.data.prd, response.casilla60.data.pt, response.casilla60.data.pv, response.casilla60.data.mc, response.casilla60.data.psi, response.casilla60.data.morena, response.casilla60.data.alianza, response.casilla60.data.fm, response.casilla60.data.col1, response.casilla60.data.col2);
        } else {
          printCasilla60(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        if (response.casilla70.data) {
          printCasilla70(response.casilla70.data.pan, response.casilla70.data.pri, response.casilla70.data.prd, response.casilla70.data.pt, response.casilla70.data.pv, response.casilla70.data.mc, response.casilla70.data.psi, response.casilla70.data.morena, response.casilla70.data.alianza, response.casilla70.data.fm, response.casilla70.data.col1, response.casilla70.data.col2);
        } else {
          printCasilla70(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
        if (response.casilla80.data) {
          printCasilla80(response.casilla80.data.pan, response.casilla80.data.pri, response.casilla80.data.prd, response.casilla80.data.pt, response.casilla80.data.pv, response.casilla80.data.mc, response.casilla80.data.psi, response.casilla80.data.morena, response.casilla80.data.alianza, response.casilla80.data.fm, response.casilla80.data.col1, response.casilla80.data.col2);
        } else {
          printCasilla80(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(errorResponse.error);
        });
  }

  public onPageChange(event: any) {
    this.first = event.first;
    if (this.keyFilter) {
      this.getByFilters(event);
    } else {
      this.getAll(event.page);
    }
  }

  public refresh() {
    this.first = 1;
    this.keyFilter = "";
    this.nameFilter = "";
    this.getAll(0);
    this.counter();
    this.graphic();
  }

  public selectedDocument(document: any) {
    console.log(document.key);
    console.log(this.selectedDocuments);
  }

  public getByFilters(event: any): void {
    this.getAllByFilters(event.page);
  }

  public getAllByFilters(page: number): void {
    this.page = page;
    this.documentoService.geyByFilters(this.value, this.keyFilter, page)
      .subscribe(response => {
        this.documentos = response.content;
        this.rows = response.pageable.pageSize;
        this.totalRecords = response.totalElements;
      },
        (errorResponse: ErrorResponse) => {
          this.toastService.showErrorToast(errorResponse.error);
        });
  }

  public verMas(): void {
    this.modalService.showMore();
  }
}
