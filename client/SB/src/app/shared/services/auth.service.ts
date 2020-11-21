import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  authUrl = "http://localhost:8020/safetylineConnexion";

  constructor(private http: HttpClient) { }

  login(model: any) {
    return this.http.post(this.authUrl, model).pipe(
      map((response: any) => {
        const user = response;
        if (user.result.succeeded) {
          localStorage.setItem('token', user.token);
        }
      })
    )
  }
  login2(model: any) {
    return of(true);
  }
}