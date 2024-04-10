
import { NgModule } from '@angular/core';
import { DialogModule } from 'primeng/dialog';
import { ToastModule } from 'primeng/toast';
import { TooltipModule } from 'primeng/tooltip';
import { ButtonModule } from 'primeng/button';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { TableModule } from 'primeng/table';
import { PaginatorModule } from 'primeng/paginator';
import { ListboxModule } from 'primeng/listbox';
import { SelectButtonModule } from 'primeng/selectbutton';

@NgModule({
  exports: [
    DialogModule,
    ToastModule,
    TooltipModule,
    ButtonModule,
    DynamicDialogModule,
    TableModule,
    PaginatorModule,
    ListboxModule,
    SelectButtonModule
  ]
})
export class PrimeNgModule { }