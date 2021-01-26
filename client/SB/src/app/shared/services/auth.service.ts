import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { logMod } from '../models/logMod-model';
import { addUsrMod } from '../models/addUsrMod-model';
import { addSuggMod } from '../models/addSuggMod-model';
import { upMod } from '../models/upMod-model';
import { resMod } from '../models/resMod-model';
import { promise } from 'protractor';
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

  login(model:logMod):Promise<void>{
    return this.http.post(this.authUrl + "safetylineConnexion", model, {observe : 'response'}).pipe(
      map((response: any) => {
        const user = response;
        if (user.status == 200) {
          sessionStorage.setItem('user', JSON.stringify(user));
          //sessionStorage.setItem('token', user.token);
          sessionStorage.setItem('username', user.body.username);
          sessionStorage.setItem('admin', user.body.admin);
          //this.currentUserSubject.next(user);
          //console.log(localStorage.getItem("username"))
          this.router.navigate(['/home']);
        } else {
          console.log("res.status = ko")
          //console.log(user.message);
        }
      })
    ).toPromise()
  }

  login2(model: logMod) { 
    sessionStorage.setItem('token', 'token');
    sessionStorage.setItem('username', model.username);
    sessionStorage.setItem('admin', 'true');
    this.router.navigate(['/home']);
    return of(true);
  }

  /*logout(){
    //this.currentUserSubject.next(null);
  }*/

  reset(model: resMod){
    return this.http.post(this.authUrl + "forgetPassword", model).pipe(
      map((response: string) => {
        if (response == null) {
          sessionStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()
  }

  reset2(model: resMod){
    //sessionStorage.removeItem('username');  //Peut remplacer par un clean
    //sessionStorage.removeItem('admin');
    sessionStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }

  delete(){
    const body = { 'username': sessionStorage.getItem('username') };
    return this.http.post(this.authUrl + "accountDelete", body).pipe(
      map((response: string) => {
        //console.log(response)
        if (response == null) {
          //sessionStorage.removeItem('username');  //Peut remplacer par un clean
          //sessionStorage.removeItem('admin');
          sessionStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response);
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

  update(model: upMod){
    return this.http.post(this.authUrl + "account", model, {withCredentials:true}).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/home']);
        } else {
          console.log(response);
        }
      })
    )/*.toPromise()*/
  }

  update2(model: upMod){
    this.router.navigate(['/home']);
    return of(true);
  }

  create(model: addUsrMod){
    return this.http.put(this.authUrl+ "account" + "/" + model.username, model).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/home']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()
  }

  create2(model: addUsrMod){
    this.router.navigate(['/home']);
    return of(true);
  }

  suggestion(model: addSuggMod){
    return this.http.post(this.authUrl + "suggestion", model, {withCredentials:true}).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/home']);
        } else {
          console.log(response);
        }
      })
    )/*.toPromise()*/
  }

  suggestion2(model: addSuggMod){
    this.router.navigate(['/home']);
    //console.log(model.text);
    return of(true);
  }

  getSuggestion2(){
    return this.http.post(this.authUrl + "suggestions",{observe : 'response'}).pipe(
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
    return this.http.get(this.authUrl + "accounts",{observe : 'response'}).pipe(
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

  deleteUser(id:string){
    const body = { 'username': id };
    return this.http.post(this.authUrl + "accountDelete", body).pipe(
      map((response: string) => {
        //console.log(response)
        if (response == "") {
          this.router.navigate(['/admin']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()
  }

}