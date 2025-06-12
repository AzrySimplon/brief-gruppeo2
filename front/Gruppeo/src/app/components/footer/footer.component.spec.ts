import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FooterComponent } from './footer.component';
import { RouterLink } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;
  let debugElement: DebugElement;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        FooterComponent,
        RouterLink,
        RouterTestingModule // Required for testing RouterLink
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create the footer component', () => {
    expect(component).toBeTruthy();
  });

  // Test footer container element
  it('should render a footer element', () => {
    const footerElement = debugElement.query(By.css('footer'));
    expect(footerElement).toBeTruthy();
  });

  // Test footer content
  it('should contain expected footer content', () => {
    const footerElement = debugElement.query(By.css('footer')).nativeElement;

    // Test for presence of content (adjust based on actual footer content)
    expect(footerElement.textContent).toBeTruthy();

    // Test for specific text content if applicable
    // For example: copyright notice, company name, etc.
    expect(footerElement.textContent).toContain('©'); // Copyright symbol
    expect(footerElement.textContent).toContain('2024'); // Current year
  });
});
