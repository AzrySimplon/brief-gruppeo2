import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderConnectedComponent } from './components/header-connected/header-connected.component';
import { HeaderConnectionComponent } from './components/header-connection/header-connection.component';
import {NgIf, NgOptimizedImage} from '@angular/common';
import { GlobalVariables } from "../globalVariables";
import { FooterComponent } from "./components/footer/footer.component";
import {AuthService} from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderConnectedComponent, HeaderConnectionComponent, NgIf, FooterComponent, NgOptimizedImage],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  private authService = inject(AuthService);
  get connected(): boolean {
    return this.authService.isConnected();
  }
}
