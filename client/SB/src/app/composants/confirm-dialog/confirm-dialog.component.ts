import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
//import { MatIconRegistry } from '@angular/material/icon';
//import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.css'] 
})
export class ConfirmDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data, public dialogRef: MatDialogRef<ConfirmDialogComponent>) {}

  ngOnInit(): void {
  }

  closeDialog(){
    this.dialogRef.close(false);
  }

}
