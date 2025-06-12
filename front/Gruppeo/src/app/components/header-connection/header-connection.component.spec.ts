import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterLink, Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { HeaderConnectionComponent } from './header-connection.component';

describe('HeaderConnectionComponent', () => {
  let component: HeaderConnectionComponent;
  let fixture: ComponentFixture<HeaderConnectionComponent>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HeaderConnectionComponent,
        RouterLink,
        RouterTestingModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(HeaderConnectionComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test button styling
  it('should apply the header-button class to all buttons', () => {
    const buttons = debugElement.queryAll(By.css('button'));

    buttons.forEach(button => {
      expect(button.nativeElement.classList.contains('header-button')).toBeTruthy();
    });
  });

  // Test navigation to register page
  it('should navigate to register page when "Inscription" is clicked', () => {
    // Set up a spy on the router
    const routerSpy = spyOn(TestBed.inject(Router), 'navigateByUrl');

    // Click the register button
    const registerButton = debugElement.queryAll(By.css('.header-button'))[0].nativeElement;
    registerButton.click();

    // Check that navigation occurred
    expect(routerSpy).toHaveBeenCalled();
    // Check that the first argument to navigateByUrl contains '/register'
    expect(routerSpy.calls.first().args[0].toString()).toContain('/register');
  });

  // Test navigation to login page
  it('should navigate to login page when "Connexion" is clicked', () => {
    // Set up a spy on the router
    const routerSpy = spyOn(TestBed.inject(Router), 'navigateByUrl');

    // Click the login button
    const loginButton = debugElement.queryAll(By.css('.header-button'))[1].nativeElement;
    loginButton.click();

    // Check that navigation occurred
    expect(routerSpy).toHaveBeenCalled();
    // Check that the first argument to navigateByUrl contains '/login'
    expect(routerSpy.calls.first().args[0].toString()).toContain('/login');
  });

  // Test that the component doesn't show account management or logout buttons
  it('should not contain account management or logout buttons', () => {
    const buttonTexts = debugElement.queryAll(By.css('.header-button'))
      .map(button => button.nativeElement.textContent.trim());

    expect(buttonTexts).not.toContain('Gérer Compte');
    expect(buttonTexts).not.toContain('Déconnexion');
  });

  // Test button order
  it('should display "Inscription" button before "Connexion" button', () => {
    const buttons = debugElement.queryAll(By.css('.header-button'));

    expect(buttons[0].nativeElement.textContent.trim()).toBe('Inscription');
    expect(buttons[1].nativeElement.textContent.trim()).toBe('Connexion');
  });

  // Test button elements are actual buttons (not links styled as buttons)
  it('should use button elements', () => {
    const buttons = debugElement.queryAll(By.css('.header-button'));

    buttons.forEach(button => {
      expect(button.nativeElement.tagName.toLowerCase()).toBe('button');
    });
  });

  // Test that both buttons have RouterLink directives
  it('should have RouterLink directives on both buttons', () => {
    const buttons = debugElement.queryAll(By.css('.header-button'));

    buttons.forEach(button => {
      expect(() => button.injector.get(RouterLink)).not.toThrow();
    });
  });
});
