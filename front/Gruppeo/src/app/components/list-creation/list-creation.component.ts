import {Component, Input, ViewChild} from '@angular/core';
import {NgForOf} from '@angular/common';
import {TextInputComponent} from '../text-input/text-input.component';
import {SearchBarComponent} from '../search-bar/search-bar.component';
import {CheckboxChoiceComponent} from '../checkbox-choice/checkbox-choice.component';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-list-creation',
  imports: [
    NgForOf,
    TextInputComponent,
    SearchBarComponent,
    FormsModule
  ],
  templateUrl: './list-creation.component.html',
  styleUrl: './list-creation.component.css'
})
export class ListCreationComponent {
  @Input() personsArray!: Person[];

  @ViewChild('input_list_name') checkboxDWWM!: CheckboxChoiceComponent;

  validate() {
    console.log('Checkbox:', this.checkboxDWWM.getValue());
  }
}
