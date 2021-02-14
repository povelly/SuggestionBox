import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { ResetComponent } from './reset/reset.component';
import { PageComponent } from './page/page.component';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AdminComponent } from './admin/admin.component';
import { ParametersComponent } from './parameters/parameters.component';
import { SondageComponent } from './sondage/sondage.component';
import { ConfirmDialogComponent } from './confirm-dialog/confirm-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ErrorConfirmComponent } from './error-confirm/error-confirm.component';
import { GuardeGuard } from '../shared/services/guarde.guard';
import {NgxPaginationModule} from 'ngx-pagination'

const routes: Routes = [
  { path: 'login', component:LoginFormComponent},
  { path: 'reset', component:ResetComponent},
  { path: 'home', component:PageComponent, canActivate: [GuardeGuard]},
  { path: 'admin', component:AdminComponent, canActivate: [GuardeGuard], data: {admin:"true"}},
  { path: 'parameters', component:ParametersComponent, canActivate: [GuardeGuard] },
  { path: 'sondage', component:SondageComponent, canActivate: [GuardeGuard], data: {admin:"true"} },
];

@NgModule({
  declarations: [LoginFormComponent, ResetComponent, PageComponent, AdminComponent, ParametersComponent, SondageComponent, ConfirmDialogComponent, ErrorConfirmComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    MatDialogModule,
    NgxPaginationModule,
  ],
  
  schemas: [ CUSTOM_ELEMENTS_SCHEMA ]
})
export class ComposantsModule { }
