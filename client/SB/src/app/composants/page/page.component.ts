import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { addSuggMod } from 'src/app/shared/models/addSuggMod-model';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  addsuggmod1: addSuggMod;

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) { }

  cond = sessionStorage.getItem('admin')=='true';
  myForm: FormGroup;

//crer un nouvel objet 
  ngOnInit(): void {
    this.myForm = this.fb.group({
      content: ['',[
        Validators.required
      ]],
      anonymous: [false],
      author:[""]
    });
  }


  clicSurBouton():void{
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.authService.delete().then(/*loginObserver*/);
  }

  cancel():void{
    sessionStorage.clear()
    //console.log(sessionStorage)
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  sugg(f: FormGroup):void{
    const loginObserver = {
    next: x => console.log('reception http'),
    error: err => console.log(err)
    };
    if(f.value.anonymous == false){
      f.value.author = sessionStorage.getItem("username");
    }
    this.addsuggmod1 = new addSuggMod(f.value.content, f.value.annonymous, f.value.author)
    this.authService.suggestion(f.value).then(/*loginObserver*/);
  }

}
