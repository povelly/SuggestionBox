import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginFormComponent } from './login-form/login-form.component';
import { ResetComponent } from './reset/reset.component';
import { PageComponent } from './page/page.component';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'login', component:LoginFormComponent },
  { path: 'reset', component:ResetComponent },
  { path: 'home', component:PageComponent },
];

@NgModule({
  declarations: [LoginFormComponent, ResetComponent, PageComponent],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class ComposantsModule { }
