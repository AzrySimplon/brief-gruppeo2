import { Component, ViewChild, Input, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckboxChoiceComponent } from '../checkbox-choice/checkbox-choice.component';
import {TextInputComponent} from '../text-input/text-input.component';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-group-creation',
  standalone: true, // Add this
  imports: [
    CheckboxChoiceComponent,
    CommonModule,
    TextInputComponent,
    FormsModule
  ],
  templateUrl: './group-creation.component.html',
  styleUrl: './group-creation.component.css'
})

export class GroupCreationComponent implements AfterViewInit {
  @ViewChild('checkbox_dwwm') checkboxDWWM!: CheckboxChoiceComponent;
  @ViewChild('checkbox_age') checkboxAge!: CheckboxChoiceComponent;
  @ViewChild('checkbox_level') checkboxLevel!: CheckboxChoiceComponent;
  @ViewChild('input_person_nbr') textInputPersonNbr!: TextInputComponent;
  @Input() list!: List;

  checkboxComponents: CheckboxChoiceComponent[] = [];

  ngAfterViewInit() {
    this.checkboxComponents = [this.checkboxDWWM, this.checkboxAge, this.checkboxLevel];
  }

  private shuffleArray(array: Person[], mixDwwm: boolean, mixAge: boolean, mixLevel: boolean): Person[] {
    let result = [...array];
    for (let i = result.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      if (
        (!mixDwwm && result[i].old_dwwm !== result[j].old_dwwm) ||
        (!mixAge && Math.abs(result[i].age - result[j].age) > 5) ||
        (!mixLevel && Math.abs(result[i].technical_knowledge - result[j].technical_knowledge) > 1)
      ) {
        continue;
      }
      [result[i], result[j]] = [result[j], result[i]];
    }
    return result;
  }

  private createGroups(persons: Person[], numberOfGroups: number): Group[] {
    const groups: Group[] = [];
    const membersPerGroup = Math.floor(persons.length / numberOfGroups);
    const remainingMembers = persons.length % numberOfGroups;

    let currentIndex = 0;
    for (let i = 0; i < numberOfGroups; i++) {
      const groupSize = i < remainingMembers ? membersPerGroup + 1 : membersPerGroup;
      groups.push({
        id: i + 1,
        name: `Group ${i + 1}`,
        nbr_persons: groupSize,
        members: persons.slice(currentIndex, currentIndex + groupSize)
      });
      currentIndex += groupSize;
    }
    return groups;
  }

  validate() {
    const mixDwwm = this.checkboxDWWM.getValue();
    const mixAge = this.checkboxAge.getValue();
    const mixLevel = this.checkboxLevel.getValue();
    const numberOfGroups = parseInt(this.textInputPersonNbr.getValue());

    if (isNaN(numberOfGroups) || numberOfGroups <= 0 || numberOfGroups > this.list.members.length) {
      console.error('Invalid number of groups');
      return;
    }

    const shuffledPersons = this.shuffleArray(this.list.members, mixDwwm, mixAge, mixLevel);
    const groups = this.createGroups(shuffledPersons, numberOfGroups);
    console.log('Generated groups:', groups);
  }
}
