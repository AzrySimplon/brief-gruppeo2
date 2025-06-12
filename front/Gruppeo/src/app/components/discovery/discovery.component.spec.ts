import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DiscoveryComponent } from './discovery.component';
import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';

// Create a mock version of PresentationImgComponent with required inputs
@Component({
  selector: 'app-presentation-img',
  template: '<div class="mock-presentation-img"></div>',
  standalone: true // Make it standalone to match real component
})
class MockPresentationImgComponent {
  @Input() img: string = '';
  @Input() text: string = '';
  @Input() text_alt: string = '';
}

describe('DiscoveryComponent', () => {
  let component: DiscoveryComponent;
  let fixture: ComponentFixture<DiscoveryComponent>;

  beforeEach(async () => {
    // Configure testing module
    await TestBed.configureTestingModule({
      imports: [
        DiscoveryComponent,
        NgIf,
        MockPresentationImgComponent // Import the mock component instead of declaring it
      ]
    })
    .overrideComponent(DiscoveryComponent, {
      set: {
        imports: [
          NgIf,
          MockPresentationImgComponent // Use the mock in component imports
        ]
      }
    })
    .compileComponents();

    // Create component fixture and instance
    fixture = TestBed.createComponent(DiscoveryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Basic test to ensure the component creates successfully
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Simplified test for template rendering
  it('should render component template', () => {
    const compiledElement = fixture.nativeElement as HTMLElement;
    const presentationImgElement = compiledElement.querySelector('app-presentation-img');
    expect(presentationImgElement).toBeTruthy();
  });
});
