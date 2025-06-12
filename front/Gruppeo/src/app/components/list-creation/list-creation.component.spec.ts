import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListCreationComponent } from './list-creation.component';
import { Component, Input } from '@angular/core';
import { NgForOf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CheckboxChoiceComponent } from '../checkbox-choice/checkbox-choice.component';
import { TextInputComponent } from '../text-input/text-input.component';
import { SearchBarComponent } from '../search-bar/search-bar.component';
import {Gender, Profile} from '../../../globalTypes';

// Create mock components to avoid needing to import all dependencies
@Component({ selector: 'app-text-input', template: '' })
class MockTextInputComponent {
  @Input() text: string = '';
  getValue() { return 'mock value'; }
}

@Component({ selector: 'app-search-bar', template: '' })
class MockSearchBarComponent {
  @Input() text: string = '';
  outputSearch = { emit: jasmine.createSpy('emit') };
}

@Component({ selector: 'app-checkbox-choice', template: '' })
class MockCheckboxChoiceComponent {
  getValue() { return true; }
}

describe('ListCreationComponent', () => {
  let component: ListCreationComponent;
  let fixture: ComponentFixture<ListCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      // Import the component under test and its dependencies
      imports: [
        ListCreationComponent,
        NgForOf,
        FormsModule,
      ],
      // Provide mock components
      providers: [
        { provide: TextInputComponent, useClass: MockTextInputComponent },
        { provide: SearchBarComponent, useClass: MockSearchBarComponent },
        { provide: CheckboxChoiceComponent, useClass: MockCheckboxChoiceComponent }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListCreationComponent);
    component = fixture.componentInstance;

    // Initialize input properties
    component.personsArray = []; // Provide an empty array to prevent null errors

    fixture.detectChanges();
  });

  // Basic test to ensure component can be created
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test that personsArray input is properly handled
  it('should accept personsArray as input', () => {
    // Arrange
    const mockPersonsArray : Person[] = [
      {
        id: 1,
        name: 'Person 1',
        gender: Gender.Man,
        french_knowledge: 5,
        old_dwwn: true,
        technical_knowledge: 3,
        profile: Profile.reserved,
        age: 25
      },
      {
        id: 2,
        name: 'Person 2',
        gender: Gender.Woman,
        french_knowledge: 4,
        old_dwwn: false,
        technical_knowledge: 4,
        profile: Profile.at_ease,
        age: 30
      }
    ];

    // Act
    component.personsArray = mockPersonsArray;
    fixture.detectChanges();

    // Assert
    expect(component.personsArray).toEqual(mockPersonsArray);
    expect(component.personsArray.length).toBe(2);
  });

  // Test the validate method
  it('should call getValue on checkbox in validate method', () => {
    // Arrange
    spyOn(console, 'log'); // Spy on console.log to verify it's called
    component.checkboxDWWM = new MockCheckboxChoiceComponent() as any;
    spyOn(component.checkboxDWWM, 'getValue').and.returnValue(true);

    // Act
    component.validate();

    // Assert
    expect(component.checkboxDWWM.getValue).toHaveBeenCalled();
    expect(console.log).toHaveBeenCalledWith('Checkbox:', true);
  });
});
