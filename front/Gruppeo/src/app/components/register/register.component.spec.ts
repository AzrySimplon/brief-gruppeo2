import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextInputComponent } from '../text-input/text-input.component';

// Create mock version of TextInputComponent
@Component({ selector: 'app-text-input', template: '' })
class MockTextInputComponent {
  @Input() text: string = '';
  getValue() { return 'mock value'; }
}

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RegisterComponent,
        FormsModule
      ],
      providers: [
        { provide: TextInputComponent, useClass: MockTextInputComponent }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test ngAfterViewInit method
  it('should initialize inputsArray in ngAfterViewInit', () => {
    // Arrange
    component.textInputActivation = new MockTextInputComponent() as any;
    component.textInputPasswordConfirmation = new MockTextInputComponent() as any;
    component.textInputPassword = new MockTextInputComponent() as any;

    // Act
    component.ngAfterViewInit();

    // Assert
    expect(component.inputsArray.length).toBe(3);
    expect(component.inputsArray).toContain(component.textInputActivation);
    expect(component.inputsArray).toContain(component.textInputPasswordConfirmation);
    expect(component.inputsArray).toContain(component.textInputPassword);
  });
});
