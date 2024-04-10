import { Component, OnInit } from '@angular/core';
import { Documento } from 'src/app/shared/api-objects/documento';
import { ErrorResponse } from 'src/app/shared/api-objects/error-response';
import { DocumentoService } from 'src/app/shared/services/documento-service';
import { ToastService } from 'src/app/shared/services/toast-service';
import { printDoughnutChart } from '../../../../assets/js/graphics.js'
import { Counter } from 'src/app/shared/api-objects/counter.js';

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
  public stateOptions: any[] = [{ label: 'Todos', value: 1 },{ label: 'Documento', value: 2 }, { label: 'Voz', value: 3 }];
  public value: number = 1;

  constructor(
    private documentoService: DocumentoService,
    private toastService: ToastService,
  ) {
  }

  ngOnInit(): void {
    this.getAll(0);
    this.counter();
  }

  public click(): void{
    if (this.keyFilter) {
      this.getAllByFilters(0);
    } else {
      this.getAll(0);
    }
  }

  public getAll(page: number): void {
    this.page = page;
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

  public counter(): void {
    this.documentoService.counter()
      .subscribe(response => {
        this.counterO = response;
        console.log(this.counterO);
        printDoughnutChart(this.counterO.noIniciado, this.counterO.captura1, this.counterO.captura2, this.counterO.validado);
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
}
