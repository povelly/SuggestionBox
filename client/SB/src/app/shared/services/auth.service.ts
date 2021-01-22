import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
//import { FormGroup } from '@angular/forms';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUrl = "http://localhost:8020/";
  /*authUrl2 = "http://localhost:8020/account";
  authUrl3 = "http://localhost:8020/accountDelete";
  authUrl5 = "http://localhost:8020/suggestion";
  authUrl6 = "http://localhost:8020/suggestions";
  authUrl7 = "http://localhost:8020/accounts";*/

  private currentUserSubject: BehaviorSubject<any>;
  public currentUser: Observable<any>;

  constructor(private http: HttpClient,private router: Router) { 
    this.currentUserSubject = new BehaviorSubject<any>(JSON.parse(sessionStorage.getItem('user')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login2(model: any) {
    return this.http.post(this.authUrl + "safetylineConnexion", model).pipe(
      map((response: any) => {
        const user = response;
        if (user.status == 200) {
          sessionStorage.setItem('user', JSON.stringify(user));
          //sessionStorage.setItem('token', user.token);
          sessionStorage.setItem('username', user.username);
          sessionStorage.setItem('admin', user.type);
          this.currentUserSubject.next(user);
          this.router.navigate(['/home']);
        } else {
          console.log(user.message);
        }
      })
    ).toPromise()
  }
  login(model: any) { 
    sessionStorage.setItem('token', 'token');
    sessionStorage.setItem('username', model.username);
    sessionStorage.setItem('admin', 'true');
    this.router.navigate(['/home']);
    return of(true);
  }

  logout(){
    this.currentUserSubject.next(null);
  }

  reset(model: any){
    return this.http.post(this.authUrl + "account", model).pipe(
      map((response: any) => {
        if (response.status == 200) {

          //sessionStorage.removeItem('username');  //Peut remplacer par un clean
          //sessionStorage.removeItem('admin');
          sessionStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response.message);
        }
      })
    ).toPromise()
  }

  reset2(model: any){
    //sessionStorage.removeItem('username');  //Peut remplacer par un clean
    //sessionStorage.removeItem('admin');
    sessionStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }

  delete(){
    const body = { 'username': sessionStorage.getItem('username') };
    return this.http.post(this.authUrl + "accountDelete", body).pipe(
      map((response: any) => {
        //console.log(response)
        if (response.status == 200) {
          //sessionStorage.removeItem('username');  //Peut remplacer par un clean
          //sessionStorage.removeItem('admin');
          sessionStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response.message);
        }
      })
    ).toPromise()
  }

  delete2(){
    //console.log('Nous supprimons le compte: ' + sessionStorage.getItem('username'))
    //sessionStorage.removeItem('username');  //Peut remplacer par un clean
    //sessionStorage.removeItem('admin');
    sessionStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }

  update(model: any){
    return this.http.post(this.authUrl + "account", model).pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/home']);
        } else {
          console.log(response.message);
        }
      })
    ).toPromise()
  }

  update2(model: any){
    this.router.navigate(['/home']);
    return of(true);
  }

  create(model: any){
    return this.http.put(this.authUrl+ "account" + "/" + model.username, model).pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/home']);
        } else {
          console.log(response.message);
        }
      })
    ).toPromise()
  }

  create2(model: any){
    this.router.navigate(['/home']);
    return of(true);
  }

  suggestion(model: any){
    return this.http.post(this.authUrl + "suggestion", model).pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/home']);
        } else {
          console.log(response.message);
        }
      })
    ).toPromise()
  }

  suggestion2(model: any){
    this.router.navigate(['/home']);
    console.log(model.text);
    return of(true);
  }

  getSuggestion2(){
    return this.http.post(this.authUrl + "suggestions",{}).pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/admin']);
          return response;
        } else {
          console.log(response.message);
        }
      })
    )
  }

  getSuggestion(){
    return [
        {
          "username":"moi",
          "text": "J'aime les pommes",
          "date": 18
        }   
    ]
  }

  getUsers2(){
    return this.http.get(this.authUrl + "accounts").pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/admin']);
          return response;
        } else {
          console.log(response.message);
        }
      })
    )
  }

  getUsers(){
    return [
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        }  
    ]
  }

  deleteUser(id:any){
    const body = { 'username': id };
    return this.http.post(this.authUrl + "accountDelete", body).pipe(
      map((response: any) => {
        //console.log(response)
        if (response.status == 200) {
          this.router.navigate(['/admin']);
        } else {
          console.log(response.message);
        }
      })
    )
  }

}