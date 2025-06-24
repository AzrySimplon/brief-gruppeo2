import {Component, inject, ViewChild} from '@angular/core';
import {TextInputComponent} from '../text-input/text-input.component';
import {FormsModule} from '@angular/forms';
import {TestService} from '../../services/test.service';

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
  @ViewChild('input_name') textInputName!: TextInputComponent;
  @ViewChild('input_pwd') textInputPassword!: TextInputComponent;
  inputsArray: TextInputComponent[] = [];
  testObject: {username: string, password: string} = {username: "", password: ""};

  private testService: TestService = inject(TestService);

  validate() {
    this.testObject = {username: this.textInputName.getValue(), password: this.textInputPassword.getValue()};
    this.testService.createUser(this.testObject).subscribe();
  }
}
