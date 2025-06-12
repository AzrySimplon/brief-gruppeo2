import {Component, ViewChild} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TextInputComponent} from '../text-input/text-input.component';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    TextInputComponent,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  @ViewChild('input_id') textInputPasswordConfirmation!: TextInputComponent;
  @ViewChild('input_pwd') textInputPassword!: TextInputComponent;
  inputsArray: TextInputComponent[] = [];

  ngAfterViewInit() {
    this.inputsArray = [this.textInputPasswordConfirmation, this.textInputPassword];
  }

  validate() {
    this.inputsArray.forEach((input: TextInputComponent) => {
      console.log('Input:', input.getValue());
    })
  }
}
