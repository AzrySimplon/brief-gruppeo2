import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HeaderConnectedComponent } from './components/header-connected/header-connected.component';
import { HeaderConnectionComponent } from './components/header-connection/header-connection.component';
import { FooterComponent } from './components/footer/footer.component';
import { GlobalVariables } from '../globalVariables';
import { Component } from '@angular/core';

// Create mock components to avoid needing to import all dependent components
// This simplifies testing by focusing only on the AppComponent's logic
@Component({ selector: 'app-header-connected', template: '' })
class MockHeaderConnectedComponent {}

@Component({ selector: 'app-header-connection', template: '' })
class MockHeaderConnectionComponent {}

@Component({ selector: 'app-footer', template: '' })
class MockFooterComponent {}

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  // Setup before each test
  beforeEach(async () => {
    // Configure the testing module with necessary imports and mocks
    await TestBed.configureTestingModule({
      imports: [
        AppComponent,
        RouterTestingModule // Required since AppComponent uses RouterOutlet
      ],
      // Provide mock components instead of real ones
      providers: [
        { provide: HeaderConnectedComponent, useClass: MockHeaderConnectedComponent },
        { provide: HeaderConnectionComponent, useClass: MockHeaderConnectionComponent },
        { provide: FooterComponent, useClass: MockFooterComponent }
      ]
    }).compileComponents();

    // Create a fixture for the AppComponent
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Test basic component creation
  it('should create the app component', () => {
    // This test ensures the component can be instantiated without errors
    expect(component).toBeTruthy();
  });



  // Test that footer is always present
  it('should always display the footer component', () => {
    // Check for footer presence regardless of connection state
    const compiledElement = fixture.nativeElement as HTMLElement;
    const footerElement = compiledElement.querySelector('app-footer');

    expect(footerElement).toBeTruthy();
  });

  // Test that RouterOutlet is present
  it('should contain router-outlet element', () => {
    // Verify the router outlet exists for rendering routed components
    const compiledElement = fixture.nativeElement as HTMLElement;
    const routerOutlet = compiledElement.querySelector('router-outlet');

    expect(routerOutlet).toBeTruthy();
  });

  // Test that the decorative images are present with correct attributes
  it('should render decorative images with correct attributes', () => {
    const compiledElement = fixture.nativeElement as HTMLElement;

    // Check for shape1 image
    const shape1 = compiledElement.querySelector('#shape1');
    expect(shape1).toBeTruthy();
    expect(shape1?.getAttribute('src')).toMatch(/shape1\.svg$/);
    expect(shape1?.getAttribute('alt')).toBe('decoration');
    expect(shape1?.getAttribute('aria-hidden')).toBe('true');

    // Check for shape2 image
    const shape2 = compiledElement.querySelector('#shape2');
    expect(shape2).toBeTruthy();
    expect(shape2?.getAttribute('src')).toMatch(/shape2\.svg$/);
    expect(shape2?.getAttribute('alt')).toBe('decoration');
    expect(shape2?.getAttribute('aria-hidden')).toBe('true');
  });
});
