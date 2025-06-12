import { Component, Input } from '@angular/core';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'app-dropdown-selection',
  imports: [
    NgForOf
  ],
  templateUrl: './dropdown-selection.component.html',
  styleUrl: './dropdown-selection.component.css'
})
export class DropdownSelectionComponent {
  @Input() title_text!: string;
  @Input() optionTextsArray!: string[];

}
