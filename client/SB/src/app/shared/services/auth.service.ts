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
import { addSondMod } from '../models/addSondMod-model';
import { repSondMod } from '../models/repSondMod-model';
import { DialogService } from './dialog.service';
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

  constructor(private http: HttpClient,private router: Router,private dialogService: DialogService) { 
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
        if (user.status == 200){
          //sessionStorage.setItem('user', JSON.stringify(user));
          sessionStorage.setItem('username', user.body.username);
          sessionStorage.setItem('admin', user.body.admin);
          this.currentUserSubject.next(user);
          //console.log(localStorage.getItem("username"))
          this.router.navigate(['/home']);
        } else {
          this.dialogService.openErrorDialog("Mail ou mot de passe incorrects").afterClosed().subscribe(res =>{});
          console.log("res.status = ko")
        }
      })
    ).toPromise()
  }

  login2(model: logMod) { 
    sessionStorage.setItem('token', 'token');
    sessionStorage.setItem('username', model.username);
    sessionStorage.setItem('admin', 'true');
    this.router.navigate(['/home']);
    //this.dialogService.openErrorDialog("Mail ou mot de passe incorrects").afterClosed().subscribe(res =>{});
    return of(true).toPromise();
  }

  /*logout(){
    //this.currentUserSubject.next(null);
  }*/

  async reset(model: resMod):Promise<void>{
    let res = await this.http.post(this.authUrl + "forgetPassword", model).toPromise();
    if(res){
      console.log(res)
    }
    this.dialogService.openErrorDialog("Mot de passe modifié").afterClosed().subscribe(res =>{});
    /*return this.http.post(this.authUrl + "forgetPassword", model).pipe(
      map((response: string) => {
        if (response == null) {
          sessionStorage.clear();
          this.router.navigate(['/login']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  reset2(model: resMod){
    //sessionStorage.removeItem('username');  //Peut remplacer par un clean
    //sessionStorage.removeItem('admin');
    sessionStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }

  async delete():Promise<void>{
    const body = { 'username': sessionStorage.getItem('username') };
    let res = await this.http.post(this.authUrl + "accountDelete", body).toPromise()
    if(res){
      console.log(res);
      //traiter le cas de supression impossible en cas de dernier admin
    }else{
      this.router.navigate(['/login']);
      sessionStorage.clear();
    }
    /*const body = { 'username': sessionStorage.getItem('username') };
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
    ).toPromise()*/
  }

  delete2(){
    //console.log('Nous supprimons le compte: ' + sessionStorage.getItem('username'))
    //sessionStorage.removeItem('username');  //Peut remplacer par un clean
    //sessionStorage.removeItem('admin');
    sessionStorage.clear();
    this.router.navigate(['/login']);
    return of(true);
  }

  async update(model: upMod):Promise<void>{
    let res = await this.http.post(this.authUrl + "account", model).toPromise();
    if (res){
      //console.log(res);
      //console.log("coucou");
    }
  }

  update2(model: upMod){
    this.router.navigate(['/home']);
    return of(true);
  }

  //Pas joli joli -> Teams cours promise
  async create(model: addUsrMod):Promise<void>{
    let res = await this.http.put(this.authUrl+ "account" + "/" + model.username, model).toPromise();
    if (res){
      console.log(res);
    }
    

    /*return this.http.put(this.authUrl+ "account" + "/" + model.username, model).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/home']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  create2(model: addUsrMod){
    this.router.navigate(['/home']);
    return of(true);
  }

  async suggestion(model: addSuggMod):Promise<void>{
    let res = await this.http.post(this.authUrl + "suggestion", model).toPromise()
    if(res){
      console.log(res);
    }
    /*return this.http.post(this.authUrl + "suggestion", model).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/home']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  suggestion2(model: addSuggMod){
    this.router.navigate(['/home']);
    //console.log(model.text);
    return of(true);
  }

  getSuggestion():any{
    return this.http.post(this.authUrl + "suggestions",{},{observe : 'response'}).pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/admin']);
          return response.body;
        } else {
          console.log(response);
        }
      })
    )
  }

  getSuggestion2(){
    return [
        {
          "author":"moi",
          "content": "J'aime les pommes",
          "begin": 18
        }   
    ]
  }

  async deleteSugg(id:string):Promise<void>{
    let res = await this.http.delete(this.authUrl + "suggestion/" + id).toPromise()
    if(res){
      console.log(res);
    }
    /*return this.http.delete(this.authUrl + "suggestion/" + id).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/admin']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  getUsers():any{
    return this.http.get(this.authUrl + "accounts",{observe : 'response'}).pipe(
      map((response: any) => {
        if (response.status == 200) {
          this.router.navigate(['/admin']);
          return response.body;
        } else {
          console.log(response);
        }
      })
    )
  }

  getUsers2(){
    return [
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        },
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        },
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        },
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        },
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        },
        {
          "username":"moi",
          "firstName": "J'aime les pommes",
          "lastName": 18
        } 
    ]
  }

  async deleteUser(id:string):Promise<void>{
    const body = { 'username': id };
    let res = await this.http.post(this.authUrl + "accountDelete", body).toPromise();
    if(res){
      console.log(res);
    }
    /*return this.http.post(this.authUrl + "accountDelete", body).pipe(
      map((response: string) => {
        //console.log(response)
        if (response == null) {
          this.router.navigate(['/admin']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  async addSondage(model: addSondMod):Promise<void>{
    let res = await this.http.put(this.authUrl+ "createStrawpoll" , model).toPromise()
    if(res){
      console.log(res);
    }
    /*return this.http.put(this.authUrl+ "createStrawpoll" , model).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/admin']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  getSondage():any{
    return this.http.get(this.authUrl + "availableStrawpolls",{observe : 'response'}).pipe(
      map((response: any) => {
        if (response.status == 200) {
          return response.body;
        } else {
          console.log(response);
        }
      })
    ).toPromise()
  }

  getSondage2(){
    return[
      {
          "title": "Le meilleur master info ?",
          "author": "suggestionboxsafetyline1@gmail.com",
          "expirationDate": "2021-02-01T00:00:00.000+00:00",
          "choicesContent": null,
          "idStrawpoll": 1,
          "choices": [
              {
                  "idChoice": 1,
                  "idStrawpoll": 1,
                  "content": "ANDROID"
              },
              {
                  "idChoice": 2,
                  "idStrawpoll": 1,
                  "content": "STL"
              },
              {
                  "idChoice": 3,
                  "idStrawpoll": 1,
                  "content": "RESAAAAAAAAAAAAAA"
              }
          ]
      }
  ]
  }

  async repSondage(model:repSondMod):Promise<void>{
    let res = await this.http.post(this.authUrl + "vote", model).toPromise()
    if(res){
      console.log(res)
    }
    /*return this.http.post(this.authUrl + "vote", model).pipe(
      map((response: string) => {
        if (response == null) {
          this.router.navigate(['/home']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }

  getResSondage():any{
    return this.http.get(this.authUrl + "strawpolls",{observe : 'response'}).pipe(
      map((response: any) => {
        if (response.status == 200) {
          return response.body;
        } else {
          console.log(response);
        }
      })
    ).toPromise()
  }

  getResSondage2(){
    return[
      {
          "title": "Le meilleur master info ?",
          "author": "suggestionboxsafetyline1@gmail.com",
          "expirationDate": "2021-02-01T00:00:00.000+00:00",
          "choicesContent": null,
          "idStrawpoll": 1,
          "choices": [
              {
                  "idChoice": 1,
                  "idStrawpoll": 1,
                  "content": "ANDROID",
                  "votersCount": 2
              },
              {
                  "idChoice": 2,
                  "idStrawpoll": 1,
                  "content": "STL",
                  "votersCount": 1
              },
              {
                  "idChoice": 3,
                  "idStrawpoll": 1,
                  "content": "RES",
                  "votersCount": 1
              }
          ]
      }
  ]
  }

  async deleteSondage(id:string):Promise<void>{
    //const body = { 'idStrawpoll': id };
    let res = await this.http.delete(this.authUrl + "strawpoll/" + id).toPromise()
    if(res){
      console.log(res)
    }

    /*return this.http.delete(this.authUrl + "strawpoll/" + id).pipe(
      map((response: string) => {
        //console.log(response)
        if (response == "") {
          this.router.navigate(['/admin']);
        } else {
          console.log(response);
        }
      })
    ).toPromise()*/
  }
}