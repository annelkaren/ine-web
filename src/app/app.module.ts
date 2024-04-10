import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    DashboardComponent,
    TablesComponent,
    SpinnerComponent
  ],
  imports: [
    BrowserModule,
    PrimeNgModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    [
      { provide: LOCALE_ID, useValue: 'es' },
      { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true }
    ],
    DocumentoService,
    ToastService,
    MessageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
