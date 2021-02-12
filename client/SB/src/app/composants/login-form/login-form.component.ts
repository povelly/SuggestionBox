import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from 'src/app/shared/services/auth.service';
//import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { logMod } from 'src/app/shared/models/logMod-model';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent implements OnInit {

  myForm: FormGroup;
  logmod1: logMod; 

  constructor(private authService: AuthService, private fb: FormBuilder,private dialogService: DialogService) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      username: ['',[
        Validators.required,
        Validators.email
      ]],
      password: ['',[
        Validators.required,
        Validators.pattern('^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).*$')
      ]],
    });

  }

  onSubmit2(f2: FormGroup):void{
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.logmod1 = new logMod(f2.value.username, f2.value.password)
    //this.authService.login(this.logmod1).subscribe(loginObserver);
    this.authService.login(this.logmod1).then().catch(() => {this.dialogService.openErrorDialog("Mail ou mot de passe incorrects").afterClosed().subscribe(res =>{});})
  }

  /*onSubmit(f: NgForm) {
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.authService.login(f.value).subscribe(loginObserver);
    //this.authService.login2(f.value)

    //console.log(f.value);  // { first: '', last: '' }
    //console.log(f.valid);  // false
  }
*/
}
