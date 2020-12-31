import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  cond = localStorage.getItem('admin')=='true';


  ngOnInit(): void {
  }


  clicSurBouton(){
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.authService.delete().subscribe(loginObserver);
  }

  cancel(){
    localStorage.clear()
    //console.log(localStorage)
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
