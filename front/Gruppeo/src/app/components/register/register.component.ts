import {Component, inject, ViewChild} from '@angular/core';
import {TextInputComponent} from '../text-input/text-input.component';
import {FormsModule} from '@angular/forms';
import {AuthService} from '../../services/auth/auth.service';
import {PersonInterface} from '../../interface/person-interface/person-interface';
import {UserInterface} from '../../interface/user-interface/user-interface';

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
  userObject: UserInterface = {username: "", password: ""};

  private testService: AuthService = inject(AuthService);

  validate(): void {
    this.userObject = {username: this.textInputName.getValue(), password: this.textInputPassword.getValue()};
    this.testService.createUser(this.userObject).subscribe();
  }
}
