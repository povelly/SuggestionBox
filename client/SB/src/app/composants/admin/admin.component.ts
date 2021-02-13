import { Component, OnInit } from '@angular/core';
import { FormGroup, NgForm, FormBuilder, Validators } from '@angular/forms';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { addUsrMod } from 'src/app/shared/models/addUsrMod-model';
import { AuthService } from 'src/app/shared/services/auth.service';
import { DialogService } from 'src/app/shared/services/dialog.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  modeCreation = true;
  modeSupp = false;
  modeSondage = false;
  modeSuggestions = false;
  consultationRes = false;

  myForm: FormGroup;
  suggestions = [];
  users = [];
  resSondages = [];
  resSondage = null;
  cond = sessionStorage.getItem('admin') == 'true';
  addusrmod1: addUsrMod;

  p: Number = 1

  constructor(private router: Router, private authService: AuthService, private fb: FormBuilder, iconRegistry: MatIconRegistry, sanitizer: DomSanitizer, private dialogService: DialogService) {


    iconRegistry.addSvgIcon(
      'thumbs-up',
      sanitizer.bypassSecurityTrustResourceUrl('assets/img/examples/thumbup-icon.svg'));

  }

  ngOnInit(): void {

    this.myForm = this.fb.group({
      username: ['', [
        Validators.required,
        Validators.email
      ]],
      lastName: ['', [
        Validators.required,
      ]],
      firstName: ['', [
        Validators.required,
      ]],
      admin: [false]
    });

    this.authService.getSuggestion().subscribe((response) => this.suggestions = response)
    this.authService.getUsers().subscribe((response) => this.users = response)

    //this.suggestions = this.authService.getSuggestion2();
    //this.users = this.authService.getUsers2();

    this.authService.getResSondage().then((response) => this.resSondages = response)
    //this.resSondages = this.authService.getResSondage2();

    //this.myForm.valueChanges.subscribe(console.log)


  }

  boutonCreation(): void {
    this.modeCreation = true
    this.modeSupp = false
    this.modeSondage = false
    this.modeSuggestions = false
  }

  boutonSupp(): void {
    this.modeCreation = false
    this.modeSupp = true
    this.modeSondage = false
    this.modeSuggestions = false
  }

  boutonSondages(): void {
    this.modeCreation = false
    this.modeSupp = false
    this.modeSondage = true
    this.modeSuggestions = false
  }

  boutonSuggestions(): void {
    this.modeCreation = false
    this.modeSupp = false
    this.modeSondage = false
    this.modeSuggestions = true
  }

  creation(f: FormGroup): void {
    const loginObserver = {
      next: x => console.log('reception http'),
      error: err => console.log(err)
    };
    this.addusrmod1 = new addUsrMod(f.value.username, f.value.lastName, f.value.firstName, f.value.admin)
    this.authService.create(this.addusrmod1).then(() => {this.router.navigate(['/home']);});
  }

  suppUser(id: string): void {
    this.dialogService.openConfirmDialog("Etes vous certains de vouloir supprimer l'utilisateur: '" + id + "' ?").afterClosed().subscribe(res => {
      if (res) {
        this.authService.deleteUser(id).then(() => {this.router.navigate(['/admin']);}); 
      }
    });
  }

  suppSugg(id: string, content: string): void {
    this.dialogService.openConfirmDialog("Etes vous certains de vouloir supprimer la suggestion: '" + content + "' ?").afterClosed().subscribe(res => {
      if (res) {
        this.authService.deleteSugg(id).then(() => {this.router.navigate(['/admin']);}); 
      }
    });
  }

  clicSurBouton(): void {
    this.dialogService.openConfirmDialog("Etes vous certains de vouloir supprimer votre compte ?").afterClosed().subscribe(res => {
      if (res) {
        this.authService.delete().then(() => { sessionStorage.clear(); this.router.navigate(['/login']); });
      }
    });
  }


  cancel(): void {
    sessionStorage.clear()
    //console.log(localStorage)
    //this.authService.logout();
    this.router.navigate(['/login']);
  }

  suppSond(sondage: any): void {
    this.dialogService.openConfirmDialog("Etes vous certains de vouloir supprimer le sondage: '" + sondage.title + "' ?").afterClosed().subscribe(res => {
      if (res) {
        this.authService.deleteSondage(sondage.idStrawpoll).then(()=>{this.router.navigate(['/admin']);});
      }
    });

  }

  consResSond(sondage: any): void {
    this.resSondage = sondage;
    this.consultationRes = true;
  }

  //this.dialogService.openErrorDialog("Error").afterClosed().subscribe(res =>{});

}
