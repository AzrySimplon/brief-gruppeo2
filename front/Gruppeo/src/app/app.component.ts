import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderConnectedComponent } from './components/header-connected/header-connected.component';
import { HeaderConnectionComponent } from './components/header-connection/header-connection.component';
import {NgIf, NgOptimizedImage} from '@angular/common';
import { GlobalVariables } from "../globalVariables";
import { FooterComponent } from "./components/footer/footer.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderConnectedComponent, HeaderConnectionComponent, NgIf, FooterComponent, NgOptimizedImage],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements GlobalVariables {
  connected = GlobalVariables.user.isConnected;
}
