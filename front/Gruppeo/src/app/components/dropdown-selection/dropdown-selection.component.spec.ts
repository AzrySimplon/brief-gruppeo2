import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DropdownSelectionComponent } from './dropdown-selection.component';
import { NgForOf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { By } from '@angular/platform-browser';

// Mock component to test optionComponentsArray
@Component({
  selector: 'app-mock-option',
  template: '<div class="mock-option">{{text}}</div>',
  standalone: true
})
class MockOptionComponent {
  @Input() text: string = '';
}

describe('DropdownSelectionComponent', () => {
  let component: DropdownSelectionComponent;
  let fixture: ComponentFixture<DropdownSelectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        DropdownSelectionComponent,
        NgForOf,
        MockOptionComponent
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DropdownSelectionComponent);
    component = fixture.componentInstance;

    // Initialize input properties with default values to prevent null errors
    component.title_text = 'Test Dropdown';
    component.optionTextsArray = ['Option 1', 'Option 2', 'Option 3'];

    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test the title_text @Input property
  it('should display the correct title text', () => {
    // Arrange
    component.title_text = 'Select an Option';
    fixture.detectChanges();

    // Act
    const titleElement = fixture.debugElement.query(By.css('.dropdown-title')).nativeElement;

    // Assert
    expect(titleElement.textContent).toContain('Select an Option');
  });

  // Test the optionTextsArray @Input property
  it('should render all options from optionTextsArray', () => {
    // Arrange
    component.optionTextsArray = ['Red', 'Green', 'Blue'];
    fixture.detectChanges();

    // Act
    const optionElements = fixture.debugElement.queryAll(By.css('.dropdown-option'));

    // Assert
    expect(optionElements.length).toBe(3);
    expect(optionElements[0].nativeElement.textContent).toContain('Red');
    expect(optionElements[1].nativeElement.textContent).toContain('Green');
    expect(optionElements[2].nativeElement.textContent).toContain('Blue');
  });

  // Test the component with empty optionTextsArray
  it('should handle empty optionTextsArray', () => {
    // Arrange
    component.optionTextsArray = [];
    fixture.detectChanges();

    // Act
    const optionElements = fixture.debugElement.queryAll(By.css('.dropdown-option'));

    // Assert
    expect(optionElements.length).toBe(0);
  });

  // Test dropdown open/close functionality (if applicable)
  it('should toggle dropdown visibility when clicked', () => {
    // Arrange
    const dropdownToggle = fixture.debugElement.query(By.css('.dropdown-toggle')).nativeElement;
    const dropdownContent = fixture.debugElement.query(By.css('.dropdown-content'));

    // Initial state - dropdown should be closed
    expect(dropdownContent.classes['show-dropdown']).toBeFalsy();

    // Act - click to open
    dropdownToggle.click();
    fixture.detectChanges();

    // Assert - dropdown should be open
    expect(dropdownContent.classes['show-dropdown']).toBeTruthy();

    // Act - click again to close
    dropdownToggle.click();
    fixture.detectChanges();

    // Assert - dropdown should be closed again
    expect(dropdownContent.classes['show-dropdown']).toBeFalsy();
  });

  // Test option selection functionality (if applicable)
  it('should select an option when clicked', () => {
    // Arrange
    component.optionTextsArray = ['Option A', 'Option B'];
    fixture.detectChanges();

    // Open the dropdown
    const dropdownToggle = fixture.debugElement.query(By.css('.dropdown-toggle')).nativeElement;
    dropdownToggle.click();
    fixture.detectChanges();

    // Act - click on an option
    const optionElements = fixture.debugElement.queryAll(By.css('.dropdown-option'));
    optionElements[1].nativeElement.click();
    fixture.detectChanges();

    // Assert
    // Check that the selected option is highlighted or marked as selected
    expect(optionElements[1].classes['selected']).toBeTruthy();

    // Check that the dropdown displays the selected value
    const selectedValueElement = fixture.debugElement.query(By.css('.selected-value')).nativeElement;
    expect(selectedValueElement.textContent).toContain('Option B');
  });

  // Test accessibility attributes
  it('should have proper accessibility attributes', () => {
    // Arrange
    const dropdownElement = fixture.debugElement.query(By.css('.dropdown')).nativeElement;
    const dropdownToggle = fixture.debugElement.query(By.css('.dropdown-toggle')).nativeElement;

    // Assert
    expect(dropdownElement.getAttribute('role')).toBe('listbox');
    expect(dropdownToggle.getAttribute('aria-haspopup')).toBe('true');
    expect(dropdownToggle.getAttribute('aria-expanded')).toBe('false');
  });

  // Test keyboard navigation (if applicable)
  it('should support keyboard navigation', () => {
    // Arrange
    component.optionTextsArray = ['Option X', 'Option Y', 'Option Z'];
    fixture.detectChanges();

    // Manually set the dropdown to open state
    component.isOpen = true;
    fixture.detectChanges();

    // Assert - dropdown should be open
    const dropdownContent = fixture.debugElement.query(By.css('.dropdown-content'));
    expect(dropdownContent.classes['show-dropdown']).toBeTruthy();

    // Manually trigger the keyboard event handler
    component.handleKeyboardEvent(new KeyboardEvent('keydown', { key: 'ArrowDown' }));
    fixture.detectChanges();

    // Assert - first option should be focused
    const optionElements = fixture.debugElement.queryAll(By.css('.dropdown-option'));
    expect(optionElements[0].classes['focused']).toBeTruthy();
  });

  // Test responsive behavior (if applicable)
  it('should have responsive design', () => {
    // This would typically be a visual test, but we can check for responsive classes
    const dropdownElement = fixture.debugElement.query(By.css('.dropdown')).nativeElement;
    expect(dropdownElement.classList.contains('responsive')).toBeTruthy();
  });
});
