import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GuardeGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {


    if ((sessionStorage.getItem("username") != null && (route.data.admin != "true" || sessionStorage.getItem("admin")=="true")))
      return true;

    this.router.navigate(['/login'])
    return false

  }

}
