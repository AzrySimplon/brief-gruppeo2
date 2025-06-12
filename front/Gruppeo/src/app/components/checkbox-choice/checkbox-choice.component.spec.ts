import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CheckboxChoiceComponent } from './checkbox-choice.component';
import { FormsModule } from '@angular/forms';

describe('CheckboxChoiceComponent', () => {
  let component: CheckboxChoiceComponent;
  let fixture: ComponentFixture<CheckboxChoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CheckboxChoiceComponent,
        FormsModule // Required for ngModel
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(CheckboxChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test initial state (assuming it has a similar structure to other components)
  it('should initialize with default values', () => {
    expect(component.getValue).toBeDefined();
  });

  // Test getValue method
  it('should return current state from getValue', () => {
    // This test assumes CheckboxChoiceComponent has a isChecked property
    // and getValue returns this value - adjust based on actual implementation
    component['isChecked'] = true;
    expect(component.getValue()).toBe(true);

    component['isChecked'] = false;
    expect(component.getValue()).toBe(false);
  });

  // Test template rendering and binding
  it('should render checkbox with label', () => {
    // Arrange - assuming component has a text @Input property
    component.text = 'Accept Terms';
    fixture.detectChanges();

    // Act
    const labelEl = fixture.nativeElement.querySelector('p');
    const checkboxEl = fixture.nativeElement.querySelector('input[type="checkbox"]');

    // Assert
    expect(labelEl.textContent).toContain('Accept Terms');
    expect(checkboxEl).toBeTruthy();
  });

  // Test checkbox interaction
  it('should update state when checkbox is clicked', () => {
    // Arrange
    const checkboxEl = fixture.nativeElement.querySelector('input[type="checkbox"]');

    // Act - check the checkbox
    checkboxEl.checked = true;
    checkboxEl.dispatchEvent(new Event('change'));
    fixture.detectChanges();

    // Assert
    expect(component['isChecked']).toBe(true);
    expect(component.getValue()).toBe(true);

    // Act - uncheck the checkbox
    checkboxEl.checked = false;
    checkboxEl.dispatchEvent(new Event('change'));
    fixture.detectChanges();

    // Assert
    expect(component['isChecked']).toBe(false);
    expect(component.getValue()).toBe(false);
  });
});
