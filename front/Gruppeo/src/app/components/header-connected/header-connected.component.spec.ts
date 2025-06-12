import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterLink } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { HeaderConnectedComponent } from './header-connected.component';

describe('HeaderConnectedComponent', () => {
  let component: HeaderConnectedComponent;
  let fixture: ComponentFixture<HeaderConnectedComponent>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HeaderConnectedComponent,
        RouterLink,
        RouterTestingModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderConnectedComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test for the presence of buttons
  it('should contain two buttons', () => {
    const buttons = debugElement.queryAll(By.css('.header-button'));
    expect(buttons.length).toBe(2);
  });

  // Test for the 'Déconnexion' button
  it('should have a "Déconnexion" button', () => {
    const logoutButton = debugElement.queryAll(By.css('.header-button'))[1];

    // Check the button text
    expect(logoutButton.nativeElement.textContent).toContain('Déconnexion');

    // Check that it doesn't have a RouterLink (assuming logout is handled by a click event)
    expect(() => logoutButton.injector.get(RouterLink)).toThrow();
  });

  // Test button styling
  it('should apply the header-button class to all buttons', () => {
    const buttons = debugElement.queryAll(By.css('button'));

    buttons.forEach(button => {
      expect(button.nativeElement.classList.contains('header-button')).toBeTruthy();
    });
  });

  // Test accessibility
  it('should have accessible buttons', () => {
    const buttons = debugElement.queryAll(By.css('.header-button'));

    buttons.forEach(button => {
      // Check that buttons are properly accessible
      expect(button.nativeElement.getAttribute('role') || button.nativeElement.tagName.toLowerCase())
        .toBe('button');

      // Check for other accessibility attributes if implemented
      // expect(button.nativeElement.getAttribute('aria-label')).toBeTruthy();
    });
  });

  // Test that the component doesn't show login/register buttons
  it('should not contain login or register buttons', () => {
    const buttonTexts = debugElement.queryAll(By.css('.header-button'))
      .map(button => button.nativeElement.textContent.trim());

    expect(buttonTexts).not.toContain('Connexion');
    expect(buttonTexts).not.toContain('Inscription');
  });
});
