import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-text-input',
  imports: [FormsModule],
  templateUrl: './text-input.component.html',
  styleUrl: './text-input.component.css'
})
export class TextInputComponent {
  @Input() text!: string;
  inputValue: string; 

  constructor() {
    this.inputValue = "";
  }

  getValue() {
    return this.inputValue;
  }
}
