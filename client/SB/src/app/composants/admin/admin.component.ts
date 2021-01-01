import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  modeCreation = true
  modeSupp = false
  modeSondage = false

  constructor() { }

  ngOnInit(): void {
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

}
