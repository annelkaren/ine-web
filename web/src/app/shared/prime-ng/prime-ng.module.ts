
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
import { CardModule } from 'primeng/card';
import { FileUploadModule } from 'primeng/fileupload';
import { ImageModule } from 'primeng/image';
import { InputNumberModule } from 'primeng/inputnumber';
import { DividerModule } from 'primeng/divider';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';


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
    SelectButtonModule,
    CardModule,
    FileUploadModule,
    ImageModule,
    InputNumberModule,
    DividerModule,
    DropdownModule,
    InputTextModule
  ]
})
export class PrimeNgModule { }