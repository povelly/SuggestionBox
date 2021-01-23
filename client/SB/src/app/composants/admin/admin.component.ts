import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { addUsrMod } from 'src/app/shared/models/addUsrMod-model';
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
  cond = sessionStorage.getItem('admin')=='true';
  addusrmod1: addUsrMod; 

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

    //this.authService.getSuggestion().subscribe((response) => this.suggestions = response.suggestions)
    //this.authService.getUsers().subscribe((response) => this.users = response.users)
    this.suggestions = this.authService.getSuggestion();
    this.users = this.authService.getUsers();
    
    //this.myForm.valueChanges.subscribe(console.log)

    
  }

  boutonCreation():void{
    this.modeCreation = true
    this.modeSupp = false
    this.modeSondage = false
  }

  boutonSupp():void{
    this.modeCreation = false
    this.modeSupp = true
    this.modeSondage = false
  }

  boutonSondages():void{
    this.modeCreation = false
    this.modeSupp = false
    this.modeSondage = true
  }

  creation(f: FormGroup):void{
    const loginObserver = {
    next: x => console.log('reception http'),
    error: err => console.log(err)
    };
    this.addusrmod1 = new addUsrMod(f.value.username, f.value.lastName, f.value.firstName, f.value.admin)
    this.authService.create(this.addusrmod1).then(/*loginObserver*/);
  }

  suppUser(id:string):void{
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
      };
      //console.log(id);
      this.authService.deleteUser(id).then();
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
    //console.log(localStorage)
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
