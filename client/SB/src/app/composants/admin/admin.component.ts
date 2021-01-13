import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  modeCreation = true;
  modeSupp = false;
  modeSondage = false;
  myForm: FormGroup;
  suggestions = [];
  users = [];
  cond = localStorage.getItem('admin')=='true';

  constructor(private router: Router, private authService: AuthService, private fb: FormBuilder,iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    
    iconRegistry.addSvgIcon(
      'thumbs-up',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/thumbup-icon.svg'));

  }

  ngOnInit(): void {

    this.myForm = this.fb.group({
      username: ['',[
        Validators.required,
        Validators.email
      ]],
      lastName: ['',[
        Validators.required,
      ]],
      firstName: ['',[
        Validators.required,
      ]],
      admin: [false]
    });

    this.authService.getSuggestion().subscribe((response) => this.suggestions = response.suggestions)
    this.authService.getUsers().subscribe((response) => this.users = response.users)
    //this.suggestions = this.authService.getSuggestion();
    //this.users = this.authService.getUsers();
    
    this.myForm.valueChanges.subscribe(console.log)

    
  }

  boutonCreation(){
    this.modeCreation = true
    this.modeSupp = false
    this.modeSondage = false
  }

  boutonSupp(){
    this.modeCreation = false
    this.modeSupp = true
    this.modeSondage = false
  }

  boutonSondages(){
    this.modeCreation = false
    this.modeSupp = false
    this.modeSondage = true
  }

  creation(f: FormGroup){
    const loginObserver = {
    next: x => console.log('reception http'),
    error: err => console.log(err)
    };
    console.log(f.value);
    this.authService.create(f.value).subscribe(loginObserver);
  }

  suppUser(id:any){
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
      };
      //console.log(id);
      this.authService.deleteUser(id).subscribe(loginObserver);
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
