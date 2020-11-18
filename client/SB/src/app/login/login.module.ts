import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', component:LoginFormComponent },
];

@NgModule({
  declarations: [LoginFormComponent],   //Indique les composants qui votn être rajoutés dans ce module
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class LoginModule { }
