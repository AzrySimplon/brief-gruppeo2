import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-header-connection',
  imports: [RouterLink],
  templateUrl: './header-connection.component.html',
  styleUrl: './header-connection.component.css'
})
export class HeaderConnectionComponent {
  constructor() {
  }
}
