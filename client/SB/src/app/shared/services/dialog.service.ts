import { Injectable } from '@angular/core';
import {MatDialog} from '@angular/material/dialog'
import { ConfirmDialogComponent } from 'src/app/composants/confirm-dialog/confirm-dialog.component';
import { ErrorConfirmComponent } from 'src/app/composants/error-confirm/error-confirm.component';


@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog) { }

  openConfirmDialog(msg:string){
    return this.dialog.open(ConfirmDialogComponent,{
      width: '390px',
      panelClass: 'confirm-dialog-container',
      disableClose: true,
      data: {
        message: msg
      }
    });
  }

  openErrorDialog(msg:string){
    return this.dialog.open(ErrorConfirmComponent,{
      width: '390px',
      panelClass: 'error-dialog-container',
      disableClose: true,
      data: {
        message: msg
      }
    });
  }
}
