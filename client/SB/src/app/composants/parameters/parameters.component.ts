import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { upMod } from 'src/app/shared/models/upMod-model';
import { DialogService } from 'src/app/shared/services/dialog.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-parameters',
  templateUrl: './parameters.component.html',
  styleUrls: ['./parameters.component.css']
})
export class ParametersComponent implements OnInit {

  myForm: FormGroup;
  upmod1: upMod;

  constructor(private authService: AuthService, private fb: FormBuilder,private dialogService: DialogService,private router: Router) { }

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

  checkPasswords(group: FormGroup):{ notSame: boolean }{ // here we have the 'passwords' group
  let pass = group.get('newPassword').value;
  let newPassword2 = group.get('newPassword2').value;

  return pass === newPassword2 ? null : { notSame: true }     
}

  onSubmit(f: FormGroup):void{
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    f.value.username = sessionStorage.getItem("username");
    this.upmod1 = new upMod(f.value.username, f.value.oldPassword, f.value.newPassword)
    this.authService.update(this.upmod1).then(()=>{
      this.dialogService.openErrorDialog("Mot de passe modifié").afterClosed().subscribe(() =>{
        this.router.navigate(['/home']);
      });}).catch(
        () => {console.log("coucou2");}
      );
    //this.authService.update(this.upmod1).subscribe(loginObserver);
  }
}