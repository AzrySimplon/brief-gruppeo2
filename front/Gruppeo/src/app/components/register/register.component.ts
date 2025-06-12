import {Component, ViewChild} from '@angular/core';
import {TextInputComponent} from '../text-input/text-input.component';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [
    TextInputComponent,
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  @ViewChild('input_activation') textInputActivation!: TextInputComponent;
  @ViewChild('input_id') textInputPasswordConfirmation!: TextInputComponent;
  @ViewChild('input_pwd') textInputPassword!: TextInputComponent;
  inputsArray: TextInputComponent[] = [];

  ngAfterViewInit() {
     this.inputsArray = [this.textInputActivation, this.textInputPasswordConfirmation, this.textInputPassword];
  }

  validate() {
    this.inputsArray.forEach((input: TextInputComponent) => {
      console.log('Input:', input.getValue());
    })
  }
}
