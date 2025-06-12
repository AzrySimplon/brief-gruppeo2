import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SearchBarComponent } from './search-bar.component';
import { FormsModule } from '@angular/forms';
import { Component } from '@angular/core';

describe('SearchBarComponent', () => {
  let component: SearchBarComponent;
  let fixture: ComponentFixture<SearchBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SearchBarComponent, FormsModule]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create sb', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize with empty inputText', () => {
    expect(component.inputText).toBe('');
  });

  it('should emit inputText value when sendAnswer is called', () => {
    // Arrange
    const testValue = 'test search';
    component.inputText = testValue;

    // Spy on the event emitter
    spyOn(component.outputSearch, 'emit');

    // Act
    component.sendAnswer();

    // Assert
    expect(component.outputSearch.emit).toHaveBeenCalledWith(testValue);
  });

  // Test that the @Input property is properly set
  it('should properly set the @Output property', () => {
    // Arrange
    const testValue = 'test output';
    let emittedValue: string | undefined;

    // Subscribe to the output event
    component.outputSearch.subscribe((value: string) => {
      emittedValue = value;
    });

    // Act
    component.inputText = testValue;
    component.sendAnswer();

    // Assert
    expect(emittedValue).toBe(testValue);
  });

  it('should handle empty input text', () => {
    // Arrange
    component.inputText = '';
    spyOn(component.outputSearch, 'emit');

    // Act
    component.sendAnswer();

    // Assert
    expect(component.outputSearch.emit).toHaveBeenCalledWith('');
  });

  it('should handle whitespace input', () => {
    // Arrange
    component.inputText = '   ';
    spyOn(component.outputSearch, 'emit');

    // Act
    component.sendAnswer();

    // Assert
    expect(component.outputSearch.emit).toHaveBeenCalledWith('   ');
  });

  it('should emit new values when sendAnswer is called multiple times', () => {
    // Arrange
    const testValues = ['first search', 'second search'];
    let emittedValues: string[] = [];

    component.outputSearch.subscribe((value: string) => {
      emittedValues.push(value);
    });

    // Act
    component.inputText = testValues[0];
    component.sendAnswer();
    component.inputText = testValues[1];
    component.sendAnswer();

    // Assert
    expect(emittedValues).toEqual(testValues);
  });

  it('should update inputText when value changes', () => {
    // Arrange
    const newValue = 'new search text';

    // Act
    component.inputText = newValue;

    // Assert
    expect(component.inputText).toBe(newValue);
  });

});
