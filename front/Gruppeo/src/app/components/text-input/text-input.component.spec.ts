import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TextInputComponent } from './text-input.component';
import { FormsModule } from '@angular/forms';
import { Component } from '@angular/core';

describe('TextInputComponent', () => {
  let component: TextInputComponent;
  let fixture: ComponentFixture<TextInputComponent>;

  // Basic setup that runs before each test
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      // Import FormsModule since the component uses it
      imports: [TextInputComponent, FormsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(TextInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Basic test to verify component creation
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test that the initial value is empty
  it('should initialize with empty inputValue', () => {
    expect(component.inputValue).toBe('');
  });

  // Test the getValue() method returns the correct value
  it('should return the correct value from getValue()', () => {
    // Arrange: Set a value
    component.inputValue = 'test value';

    // Act: Call the method
    const result = component.getValue();

    // Assert: Check the result
    expect(result).toBe("test value");
  });

  // Test that the @Input property is properly set
  it('should properly set the @Input text property', () => {
    // Arrange: Set the input property
    component.text = 'Label Text';
    fixture.detectChanges();

    // Assert: Check the property is set correctly
    expect(component.text).toBe('Label Text');
  });

  // Test the component's template rendering
  it('should render input with label', () => {
    // Arrange: Set input property
    component.text = 'Username';
    fixture.detectChanges();

    // Act: Get the label and input elements
    const compiled = fixture.nativeElement as HTMLElement;
    const labelElement = compiled.querySelector('p');
    const inputElement = compiled.querySelector('input');

    // Assert: Check elements exist and have correct content
    expect(labelElement).toBeTruthy();
    expect(labelElement?.textContent).toContain('Username');
    expect(inputElement).toBeTruthy();
  });

  // Test that the component's two-way binding works
  it('should update inputValue when user types in the input', () => {
    // Arrange: Get the input element
    const inputElement = fixture.nativeElement.querySelector('input');

    // Act: Simulate user typing
    inputElement.value = 'new value';
    inputElement.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    // Assert: Check component state is updated
    expect(component.inputValue).toBe('new value');
  });
});
