import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUrl = "http://localhost:8020/safetylineConnexion";

  constructor(private http: HttpClient,private router: Router) { }

  login(model: any) {
    return this.http.post(this.authUrl, model).pipe(
      map((response: any) => {
        const user = response;
        if (user.status == 200) {
          localStorage.setItem('username', user.username);
          localStorage.setItem('admin', user.type);
          this.router.navigate(['/home']);
        } else {
          console.log(user.message);
        }
      })
    )
  }
  login2(model: any) { 
    localStorage.setItem('username', model.username);
    localStorage.setItem('admin', 'true');
    this.router.navigate(['/home']);
    return of(true);
  }

  reset(model: any){
    return this.http.post(this.authUrl, model).pipe(
      map((response: any) => {
        if (response.status == 200) {
          localStorage.removeItem('username');  //Peut remplacer par un clean
          localStorage.removeItem('admin');
          this.router.navigate(['/login']);
        } else {
          console.log(response.message);
        }
      })
    )
  }

  reset2(model: any){
    localStorage.removeItem('username');  //Peut remplacer par un clean
    localStorage.removeItem('admin');
    this.router.navigate(['/login']);
    return of(true);
  }

  delete(){
    const headers = { 'Authorization': localStorage.getItem('username') };
    return this.http.delete(this.authUrl,  { headers } ).pipe(
      map((response: any) => {
        //console.log(response)
        if (response.status == 200) {
          localStorage.removeItem('username');  //Peut remplacer par un clean
          localStorage.removeItem('admin');
          this.router.navigate(['/login']);
        } else {
          console.log(response.message);
        }
      })
    )
  }

  delete2(){
    console.log('Nous supprimons le compte: ' + localStorage.getItem('username'))
    localStorage.removeItem('username');  //Peut remplacer par un clean
    localStorage.removeItem('admin');
    this.router.navigate(['/login']);
    return of(true);
  }
}