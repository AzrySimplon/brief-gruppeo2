import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { LegalMentionsComponent } from './legal-mentions.component';

describe('LegalMentionsComponent', () => {
  let component: LegalMentionsComponent;
  let fixture: ComponentFixture<LegalMentionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LegalMentionsComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(LegalMentionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test that the component renders content
  it('should have content in the template', () => {
    const element = fixture.debugElement.nativeElement;
    expect(element.textContent).toBeTruthy();
  });

  // Test that the template contains expected sections (assuming legal mentions has these sections)
  it('should contain legal information sections', () => {
    const element = fixture.debugElement.nativeElement;

    // Adjust these selectors based on your actual implementation
    const headings = fixture.debugElement.queryAll(By.css('h1, h2, h3, h4, h5, h6'));

    // Expect at least one heading in a legal mentions page
    expect(headings.length).toBeGreaterThan(0);
  });
});
