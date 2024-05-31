import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './pages/shared/header/header.component';
import { FooterComponent } from './pages/shared/footer/footer.component';
import { SidebarComponent } from './pages/shared/sidebar/sidebar.component';
import { DashboardComponent } from './pages/admin/dashboard/dashboard.component';
import { TablesComponent } from './pages/admin/tables/tables.component';
import { SpinnerComponent } from './pages/shared/spinner/spinner.component';
import { PrimeNgModule } from './shared/prime-ng/prime-ng.module';
import { DocumentoService } from './shared/services/documento-service';
import { LoadingInterceptor } from './helpers/loading.interceptor';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { ToastService } from './shared/services/toast-service';
import { MessageService } from 'primeng/api';
import { AddComponent } from './pages/shared/modals/add/add.component';
import { ModalService } from './shared/services/modal-service';
import { DialogService } from 'primeng/dynamicdialog';
import {
  FormsModule,
  ReactiveFormsModule
} from '@angular/forms';
import { FormularioService } from './shared/services/formulario-service';
import { NoValidComponent } from './pages/shared/modals/no-valid/no-valid.component';
import { ValidateComponent } from './pages/shared/modals/validate/validate.component';
import { SeccionService } from './shared/services/seccion-service';
import { CasillaService } from './shared/services/casilla-service';
import { CasillaComponent } from './pages/shared/modals/casilla/casilla.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    DashboardComponent,
    TablesComponent,
    SpinnerComponent,
    AddComponent,
    NoValidComponent,
    ValidateComponent,
    CasillaComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    PrimeNgModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    [
      { provide: LOCALE_ID, useValue: 'es' },
      { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true }
    ],
    DocumentoService,
    ToastService,
    MessageService,
    DialogService,
    ModalService,
    FormularioService,
    SeccionService,
    CasillaService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
