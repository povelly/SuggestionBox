import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) { }

  cond = localStorage.getItem('admin')=='true';
  myForm: FormGroup;


  ngOnInit(): void {
    this.myForm = this.fb.group({
      content: ['',[
        Validators.required
      ]],
      anonymous: [false],
      author:[""]
    });
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

  sugg(f: FormGroup){
    const loginObserver = {
    next: x => console.log('reception http'),
    error: err => console.log(err)
    };
    if(f.value.anonymous == false){
      f.value.author = localStorage.getItem("username");
    }
    console.log(f.value);
    this.authService.suggestion(f.value).subscribe(loginObserver);
  }

}
