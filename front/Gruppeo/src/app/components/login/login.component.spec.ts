import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import { RouterLink } from '@angular/router';
import { LoginComponent } from './login.component';
import { Component, Input } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { CommonModule } from '@angular/common';

// Mock TextInputComponent to avoid dependencies
@Component({
  selector: 'app-text-input',
  template: '<div>Mocked Text Input</div>',
  standalone: true // Add standalone flag
})
class MockTextInputComponent {
  @Input() text: string = '';
  inputValue: string = 'test-value';

  getValue() {
    return this.inputValue;
  }
}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterTestingModule,
        LoginComponent,
        MockTextInputComponent // Import the mock component
      ]
    })
    .overrideComponent(LoginComponent, {
      set: {
        imports: [
          CommonModule,
          FormsModule,
          ReactiveFormsModule,
          RouterLink,
          MockTextInputComponent // Include mock in component imports
        ],
        // Use a template that matches the structure of the original (preserving ViewChild references)
        template: `
          <h2 class="category-title">Connexion</h2>
          <form class="main-container">
            <app-text-input [text]="'Identifiant'" #input_id></app-text-input>
            <app-text-input [text]="'Mot de passe'" #input_pwd></app-text-input>
            <button class="classic-button" type="submit">Se Connecter</button>
          </form>
        `
      }
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
