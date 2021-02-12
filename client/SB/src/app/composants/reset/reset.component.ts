import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { resMod } from 'src/app/shared/models/resMod-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css']
})
export class ResetComponent implements OnInit {

  resmod1:resMod;
  myForm: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder,private router: Router) { }

  ngOnInit(): void{
    this.myForm = this.fb.group({
      username: ['',[
        Validators.required,
        Validators.email
      ]]
    });

    //this.myForm.valueChanges.subscribe(console.log)
  }

  onSubmit2(f2: FormGroup):void{
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.resmod1 = new resMod(f2.value.username)
    this.authService.reset(this.resmod1).then(()=>{this.router.navigate(['/login']);});

  }

}
