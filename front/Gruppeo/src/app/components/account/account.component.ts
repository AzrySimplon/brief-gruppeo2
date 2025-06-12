import { Component, ViewChild } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {TextInputComponent} from '../text-input/text-input.component';

@Component({
  selector: 'app-account',
  imports: [
    FormsModule,
    TextInputComponent
  ],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent {
  @ViewChild('input_name') textInputName!: TextInputComponent;
  @ViewChild('confirm_dialog') confirmModal!: HTMLDialogElement;


  validate() {
    console.log('Input:', this.textInputName.getValue());
  }

  validationRequest() {
  }

  confirmAction() {
    // Handle the confirmation action
    console.log('Action confirmed');
    this.confirmModal.close();
  }


}
