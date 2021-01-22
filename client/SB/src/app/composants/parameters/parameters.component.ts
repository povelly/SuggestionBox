import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-parameters',
  templateUrl: './parameters.component.html',
  styleUrls: ['./parameters.component.css']
})
export class ParametersComponent implements OnInit {

  myForm: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      oldPassword: ['',[
        Validators.required,
        Validators.pattern('^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$')
      ]],
      newPassword: ['',[
        Validators.required,
        Validators.pattern('^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$')
      ]],
      newPassword2: ['',[
        Validators.required,
        Validators.pattern('^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$'),
      ]],
    },{validator: this.checkPasswords }
    
    );

    //this.myForm.valueChanges.subscribe(console.log)
  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
  let pass = group.get('newPassword').value;
  let newPassword2 = group.get('newPassword2').value;

  return pass === newPassword2 ? null : { notSame: true }     
}

  onSubmit(f: FormGroup){
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    f.value.username = localStorage.getItem("username");
    this.authService.update(f.value).then(/*loginObserver*/);
  }
}