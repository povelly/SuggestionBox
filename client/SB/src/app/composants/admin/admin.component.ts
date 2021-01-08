import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
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

  constructor(private authService: AuthService, private fb: FormBuilder) { }

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
    //this.suggestions = this.authService.getSuggestion();
    
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

}
