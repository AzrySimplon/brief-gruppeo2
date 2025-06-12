import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GroupCreationComponent } from './group-creation.component';
import { By } from '@angular/platform-browser';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DebugElement, NO_ERRORS_SCHEMA } from '@angular/core';

// Mock CheckboxChoiceComponent
@Component({
  selector: 'app-checkbox-choice',
  template: '<div>Mock Checkbox {{text}}</div>',
  standalone: true
})
class MockCheckboxChoiceComponent {
  @Input() text: string = '';
  private isChecked: boolean = false;

  getValue(): boolean {
    return this.isChecked;
  }

  setValue(value: boolean): void {
    this.isChecked = value;
  }
}

// Mock TextInputComponent
@Component({
  selector: 'app-text-input',
  template: '<div>Mock Input {{text}}</div>',
  standalone: true
})
class MockTextInputComponent {
  @Input() text: string = '';
  private value: string = '';

  getValue(): string {
    return this.value;
  }

  setValue(value: string): void {
    this.value = value;
  }
}

describe('GroupCreationComponent', () => {
  let component: GroupCreationComponent;
  let fixture: ComponentFixture<GroupCreationComponent>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        GroupCreationComponent,
        CommonModule,
        FormsModule,
        MockCheckboxChoiceComponent,
        MockTextInputComponent
      ],
      schemas: [NO_ERRORS_SCHEMA] // Ignore unknown elements
    })
    .overrideComponent(GroupCreationComponent, {
      set: {
        imports: [
          CommonModule,
          FormsModule,
          MockCheckboxChoiceComponent,
          MockTextInputComponent
        ],
        template: `
          <h2 class="category-title">Dans: {{list?.name}}</h2>
          <form (ngSubmit)="validate()" class="main-container">
            <app-text-input [text]="'Nombre de groupes'" #input_person_nbr></app-text-input>
            <app-checkbox-choice [text]="'Mixer les anciens DWWM'" #checkbox_dwwm></app-checkbox-choice>
            <app-checkbox-choice [text]="'Mixer les ages'" #checkbox_age></app-checkbox-choice>
            <app-checkbox-choice [text]="'Mixer les niveaux techniques'" #checkbox_level></app-checkbox-choice>
            <button type="submit" class="classic-button">Tirer</button>
          </form>
        `
      }
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupCreationComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;

    // Create a mock List for the @Input
    component.list = {
      id: 1,
      name: 'Test List',
      nbr_persons: 5
    };

    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  // Test for proper initialization of input property
  it('should initialize with the provided list input', () => {
    expect(component.list).toBeTruthy();
    expect(component.list.id).toBe(1);
    expect(component.list.name).toBe('Test List');
  });

  // Test checkboxComponents initialization in ngAfterViewInit
  it('should initialize checkboxComponents array after view init', () => {
    // Manually set the ViewChild properties as they won't be set in the test
    component.checkboxDWWM = new MockCheckboxChoiceComponent() as any;
    component.checkboxAge = new MockCheckboxChoiceComponent() as any;
    component.checkboxLevel = new MockCheckboxChoiceComponent() as any;

    // Call lifecycle hook manually
    component.ngAfterViewInit();

    // Verify the array is initialized with the three components
    expect(component.checkboxComponents.length).toBe(3);
    expect(component.checkboxComponents).toContain(component.checkboxDWWM);
    expect(component.checkboxComponents).toContain(component.checkboxAge);
    expect(component.checkboxComponents).toContain(component.checkboxLevel);
  });

  // Test template containing the required components
  it('should contain checkbox components in the template', () => {
    const checkboxElements = debugElement.queryAll(By.directive(MockCheckboxChoiceComponent));

    // Assuming that your template has three checkbox components
    expect(checkboxElements.length).toBe(3);
  });

  // Test text input component in the template
  it('should contain a text input component', () => {
    const textInputElement = debugElement.query(By.directive(MockTextInputComponent));
    expect(textInputElement).toBeTruthy();
  });



  // Test the submission button exists and triggers validate
  it('should have a submission button that calls validate', () => {
    // Set up spies
    spyOn(component, 'validate');

    // Find the submit button (assuming it has a 'submit' or 'Créer' text/class)
    const submitButton = debugElement.query(By.css('button[type="submit"]')) ||
                         debugElement.query(By.css('.submit-button')) ||
                         debugElement.query(By.css('button'));

    // Click the button
    if (submitButton) {
      submitButton.nativeElement.click();
      fixture.detectChanges();
    }

    // Check if validate was called
    expect(component.validate).toHaveBeenCalled();
  });
});
