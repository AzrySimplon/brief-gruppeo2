import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AccountComponent } from './account.component';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TextInputComponent } from '../text-input/text-input.component';

// Create mock version of TextInputComponent to simplify testing
@Component({ selector: 'app-text-input', template: '' })
class MockTextInputComponent {
  @Input() text: string = '';
  getValue() { return 'mock value'; }
}

describe('AccountComponent', () => {
  let component: AccountComponent;
  let fixture: ComponentFixture<AccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        AccountComponent,
        FormsModule
      ],
      providers: [
        { provide: TextInputComponent, useClass: MockTextInputComponent }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
