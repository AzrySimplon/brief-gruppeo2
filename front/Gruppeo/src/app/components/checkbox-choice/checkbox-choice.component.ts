import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-checkbox-choice',
  standalone: true, // Add this
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './checkbox-choice.component.html',
  styleUrl: './checkbox-choice.component.css'
})
export class CheckboxChoiceComponent {
  @Input() text!: string;
  isChecked: boolean;

  constructor() {
    this.isChecked = false;
  }

  getValue() {
    return this.isChecked;
  }
}
