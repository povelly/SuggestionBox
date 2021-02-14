import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { addSondMod } from 'src/app/shared/models/addSondMod-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sondage',
  templateUrl: './sondage.component.html',
  styleUrls: ['./sondage.component.css']
})
export class SondageComponent implements OnInit {
  
  myForm: FormGroup;
  addsondmod1: addSondMod;

  constructor(private authService: AuthService, private fb: FormBuilder,private router: Router) { }

  ngOnInit(): void {
    this.myForm = this.fb.group({
      title: ['',[
        Validators.required
      ]],
      expirationDate: ['',[
        Validators.required
      ]],
      choice1: ['',[
        Validators.required
      ]],
      choice2: ['',[
        Validators.required
      ]],
      choice3: ['',[
      ]],
      choice4: ['',[
      ]],
      choice5: ['',[
      ]],
    });
  }
  onSubmit(f: FormGroup):void{
    this.addsondmod1 = new addSondMod(f.value.title, sessionStorage.getItem("username"), f.value.expirationDate, f.value.choice1, f.value.choice2, f.value.choice3, f.value.choice4, f.value.choice5);
    //console.log(this.addsondmod1)
    this.authService.addSondage(this.addsondmod1).then(() => {this.router.navigate(['/admin']);});
  }
}


