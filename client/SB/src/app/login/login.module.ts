import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { ResetComponent } from './reset/reset.component';

const routes: Routes = [
  { path: '', component:LoginFormComponent },
  { path: 'register', component:RegisterComponent },
  { path: 'reset', component:ResetComponent },
];

@NgModule({
  declarations: [LoginFormComponent, RegisterComponent, ResetComponent],   //Indique les composants qui votn être rajoutés dans ce module
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class LoginModule { }
