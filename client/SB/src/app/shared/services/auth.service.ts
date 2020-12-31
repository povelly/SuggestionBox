import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUrl = "http://localhost:8020/safetylineConnexion";
  authUrl2 = "http://localhost:8020/account";

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient,private router: Router) { 
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(localStorage.getItem('user')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login2(model: any) {
    return this.http.post(this.authUrl, model).pipe(
      map((response: any) => {
        const user = response;
        if (user.status == 200) {
          localStorage.setItem('user', JSON.stringify(user));
          localStorage.setItem('token', user.token);
          localStorage.setItem('username', user.username);
          localStorage.setItem('admin', user.type);
          this.currentUserSubject.next(user);
          this.router.navigate(['/home']);
        } else {
          console.log(user.message);
        }
      })
    )
  }
  login(model: any) { 
    localStorage.setItem('token', 'token');
    localStorage.setItem('username', model.username);
    localStorage.setItem('admin', 'true');
    this.router.navigate(['/home']);
    return of(true);
  }

  logout(){
    this.currentUserSubject.next(null);
  }

  reset2(model: any){
    return this.http.post(this.authUrl2, model).pipe(
      map((response: any) => {
        if (response.status == 200) {
          //localStorage.removeItem('username');  //Peut remplacer par un clean
          //localStorage.removeItem('admin');
          localStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response.message);
        }
      })
    )
  }

  reset(model: any){
    //localStorage.removeItem('username');  //Peut remplacer par un clean
    //localStorage.removeItem('admin');
    localStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }

  delete2(){
    const headers = { 'username': localStorage.getItem('username') };
    return this.http.delete(this.authUrl2,  { headers } ).pipe(
      map((response: any) => {
        //console.log(response)
        if (response.status == 200) {
          //localStorage.removeItem('username');  //Peut remplacer par un clean
          //localStorage.removeItem('admin');
          localStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response.message);
        }
      })
    )
  }

  delete(){
    console.log('Nous supprimons le compte: ' + localStorage.getItem('username'))
    //localStorage.removeItem('username');  //Peut remplacer par un clean
    //localStorage.removeItem('admin');
    localStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }
}