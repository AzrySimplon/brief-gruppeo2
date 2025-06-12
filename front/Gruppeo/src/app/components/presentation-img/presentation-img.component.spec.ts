import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NgOptimizedImage } from '@angular/common';
import { By } from '@angular/platform-browser';
import { PresentationImgComponent } from './presentation-img.component';

describe('PresentationImgComponent', () => {
  let component: PresentationImgComponent;
  let fixture: ComponentFixture<PresentationImgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        PresentationImgComponent,
        NgOptimizedImage
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(PresentationImgComponent);
    component = fixture.componentInstance;
  });

  // Test component creation
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test component with input properties
  it('should properly receive and set input properties', () => {
    // Set input values
    component.img = 'test-image.jpg';
    component.text = 'Test Text';
    component.text_alt = 'Test Alt Text';

    // Trigger change detection
    fixture.detectChanges();

    // Verify the properties are set
    expect(component.img).toBe('test-image.jpg');
    expect(component.text).toBe('Test Text');
    expect(component.text_alt).toBe('Test Alt Text');
  });

  // Test that the image is properly displayed with the provided source
  it('should display the image with correct source', () => {
    // Set input value for the image
    component.img = 'test-image.jpg';
    component.text_alt = 'Test Alt Text';

    // Trigger change detection
    fixture.detectChanges();

    // Find the image element
    const imgElement = fixture.debugElement.query(By.css('img'));

    // Verify the image source contains the provided value
    // Note: NgOptimizedImage may modify the source URL
    expect(imgElement).toBeTruthy();
    expect(imgElement.nativeElement.src).toContain('test-image.jpg');
  });

  // Test that the alt text is correctly set
  it('should set the correct alt text on the image', () => {
    // Set input values
    component.img = 'test-image.jpg';
    component.text_alt = 'Test Alt Text';

    // Trigger change detection
    fixture.detectChanges();

    // Find the image element
    const imgElement = fixture.debugElement.query(By.css('img'));

    // Verify the alt text
    expect(imgElement.nativeElement.alt).toBe('Test Alt Text');
  });

  // Test that the component displays the text content
  it('should display the provided text content', () => {
    // Set input values
    component.text = 'Test Presentation Text';

    // Trigger change detection
    fixture.detectChanges();

    // Find elements that might contain the text
    // This will depend on your actual template structure
    const textElement = fixture.debugElement.query(By.css('p, h1, h2, h3, h4, h5, h6, div, span'));

    // Verify the text content is displayed
    expect(textElement).toBeTruthy();
    expect(textElement.nativeElement.textContent).toContain('Test Presentation Text');
  });

  // Test for handling undefined inputs
  it('should handle undefined input properties gracefully', () => {
    // Don't set any input properties
    fixture.detectChanges();

    // Component should still render without errors
    expect(() => fixture.detectChanges()).not.toThrow();
  });
});
