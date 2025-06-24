import {Component, inject, ViewChild} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TextInputComponent} from '../text-input/text-input.component';
import {RouterLink} from '@angular/router';
import {AuthService} from '../../services/auth/auth.service';

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
  @ViewChild('input_id') textInputId!: TextInputComponent;
  @ViewChild('input_pwd') textInputPassword!: TextInputComponent;
  userObject: {username: string, password: string} = {username: "", password: ""};
  authService: AuthService = inject(AuthService);

  validate() {
    this.userObject = {username: this.textInputId.getValue(), password: this.textInputPassword.getValue()};
    this.authService.loginUser(this.userObject);

  }
}
