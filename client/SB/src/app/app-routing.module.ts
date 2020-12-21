import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router'; // CLI imports router

const routes: Routes = [
    /*{ path: 'home', 
    loadChildren: () => import("./home/home.module").then(m => m.HomeModule)},  //Ici modifier le routage 
    { path: 'login',
    loadChildren: () => import("./login/login.module").then(m => m.LoginModule) }, //url */
    { path: '',
    loadChildren: () => import("./composants/composants.module").then(m => m.ComposantsModule) },
  ];
  
  // sets up routes constant where you define your routes

// configures NgModule imports and exports
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }