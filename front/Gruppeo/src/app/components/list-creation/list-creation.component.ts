import {Component, inject, Input, ViewChild} from '@angular/core';
import {AsyncPipe} from '@angular/common';
import {TextInputComponent} from '../text-input/text-input.component';
import {SearchBarComponent} from '../search-bar/search-bar.component';
import {FormsModule} from '@angular/forms';
import {PersonService} from '../../services/person/person.service';
import {Observable} from 'rxjs';
import {PersonInterface} from '../../interface/person-interface/person-interface';
import {ListService} from '../../services/list/list.service';
import {ListInterface} from '../../interface/list-interface/list-interface';

@Component({
  selector: 'app-list-interface-creation',
  imports: [
    TextInputComponent,
    SearchBarComponent,
    FormsModule,
    AsyncPipe
  ],
  templateUrl: './list-creation.component.html',
  styleUrl: './list-creation.component.css'
})
export class ListCreationComponent {
  private personService: PersonService = inject(PersonService);
  private listService: ListService = inject(ListService);

  @Input() personsArray$: Observable<PersonInterface[]> = this.personService.getAllPersons();

  @ViewChild('input_list_name') inputListName!: TextInputComponent;

  validate() {
    console.log('Input:', this.inputListName.getValue());

    let newList: ListInterface = {
      name: this.inputListName.getValue(),
      members: [],
      number_of_members: 0
    }

    this.listService.createList(newList);
  }
}
