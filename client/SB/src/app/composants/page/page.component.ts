import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/shared/services/auth.service';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { addSuggMod } from 'src/app/shared/models/addSuggMod-model';
import { repSondMod } from 'src/app/shared/models/repSondMod-model';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-page',
  templateUrl: './page.component.html',
  styleUrls: ['./page.component.css']
})
export class PageComponent implements OnInit {

  modeSondage = false;
  modeSuggestions = true;
  consultation = false;

  sondages = [];
  sondage = null;

  addsuggmod1: addSuggMod;
  repsondmod1: repSondMod;

  p:Number=1;

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder, private dialogService: DialogService) { }

  cond = sessionStorage.getItem('admin')=='true';
  myForm: FormGroup;
  myForm2: FormGroup;

//crer un nouvel objet 
  ngOnInit(): void {
    this.myForm = this.fb.group({
      content: ['',[
        Validators.required
      ]],
      anonymous: [false],
      author:[""]
    });

    this.myForm2 = this.fb.group({
      rep0:[false],
      rep1:[false],
      rep2:[false],
      rep3:[false],
      rep4:[false]
    });

    this.authService.getSondage().then((response) => this.sondages = response)
    //this.sondages = this.authService.getSondage2();

  }


  clicSurBouton():void{
    this.dialogService.openConfirmDialog("Êtes-vous certains de vouloir supprimer votre compte ?").afterClosed().subscribe(res => {
      if (res) {
        this.authService.delete().then(() => { 
          sessionStorage.clear(); this.router.navigate(['/login']); }).catch(
            (err)=>{
              this.dialogService.openErrorDialog("Impossible de supprimer votre compte car vous êtes le derneir admin").afterClosed().subscribe(() =>{});
            }
          );
      }
    });
    
  }

  cancel():void{
    sessionStorage.clear()
    //console.log(sessionStorage)
    //this.authService.logout();
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
    this.authService.suggestion(this.addsuggmod1).then(() =>{location.reload();});
    //this.authService.suggestion(this.addsuggmod1).subscribe(loginObserver);
  }

  reponse(f: FormGroup):void{
    //console.log(f.value)
    let reponses:string[] = []
    if (f.value.rep0) reponses.push(this.sondage.choices[0].idChoice)
    if (f.value.rep1) reponses.push(this.sondage.choices[1].idChoice)
    if (f.value.rep2) reponses.push(this.sondage.choices[2].idChoice)
    if (f.value.rep3) reponses.push(this.sondage.choices[3].idChoice)
    if (f.value.rep4) reponses.push(this.sondage.choices[4].idChoice)
    //console.log(reponses)
    this.repsondmod1 = new repSondMod(this.sondage.idStrawpoll, sessionStorage.getItem("username"), reponses)
    //console.log(this.repsondmod1)
    this.authService.repSondage(this.repsondmod1).then(() => {location.reload();}).catch(
      (err) => {
        if(err.status == 500){
          this.dialogService.openErrorDialog("Vous avez déjà voté pour ce sondage.").afterClosed().subscribe(() =>{});
        }
      }
    );
  }

  consSond(sondage: any):void{
    this.sondage = sondage;
    this.consultation = true;
  }

  boutonSondages():void{
    this.modeSondage = true
    this.modeSuggestions = false
  }

  boutonSuggestions():void{
    this.modeSondage = false
    this.modeSuggestions = true
  }

}
