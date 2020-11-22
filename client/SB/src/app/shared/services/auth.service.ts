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
        if (user.result) {
          localStorage.setItem('username', user.username);
          localStorage.setItem('type', user.type);
          this.router.navigate(['/home']);
        }
      })
    )
  }
  login2(model: any) {
    this.router.navigate(['/home']);
    return of(true);
  }
}