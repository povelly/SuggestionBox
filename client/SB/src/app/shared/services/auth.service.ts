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

  login2(model: any) {
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
  login(model: any) { 
    localStorage.setItem('username', model.username);
    localStorage.setItem('admin', 'True');
    this.router.navigate(['/home']);
    return of(true);
  }

  reset2(model: any){
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

  reset(model: any){
    localStorage.removeItem('username');  //Peut remplacer par un clean
    localStorage.removeItem('admin');
    this.router.navigate(['/login']);
    return of(true);
  }

  delete2(){
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

  delete(){
    console.log('Nous supprimons le compte: ' + localStorage.getItem('username'))
    localStorage.removeItem('username');  //Peut remplacer par un clean
    localStorage.removeItem('admin');
    this.router.navigate(['/login']);
    return of(true);
  }
}