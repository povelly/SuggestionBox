import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/shared/services/auth.service';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.css']
})
export class ResetComponent implements OnInit {

  myForm: FormGroup;

  constructor(private authService: AuthService, private fb: FormBuilder) { }

  ngOnInit(): void{
    this.myForm = this.fb.group({
      username: ['',[
        Validators.required,
        Validators.email
      ]]
    });

    this.myForm.valueChanges.subscribe(console.log)
  }

  onSubmit2(f2: FormGroup){
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.authService.reset(f2.value).then(/*loginObserver*/);

  }

}
