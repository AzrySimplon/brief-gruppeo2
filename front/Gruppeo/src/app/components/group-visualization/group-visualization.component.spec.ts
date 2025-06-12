import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GroupVisualizationComponent } from './group-visualization.component';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';
import { RouterLink } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { NgForOf } from '@angular/common';
import { Component, Input } from '@angular/core';
import { GlobalVariables } from '../../../globalVariables';

// Mock DropdownSelectionComponent
@Component({
  selector: 'app-dropdown-selection',
  template: '<div class="mock-dropdown">{{title_text}}</div>',
  standalone: true
})
class MockDropdownSelectionComponent {
  @Input() title_text: string = '';
  @Input() optionTextsArray: string[] = [];
  @Input() optionComponentsArray: any[] = [];
}

// Use global interfaces for testing
interface GroupWithNames extends Group {
  memberNames: string[];
}

describe('GroupVisualizationComponent', () => {
  let component: GroupVisualizationComponent;
  let fixture: ComponentFixture<GroupVisualizationComponent>;
  let debugElement: DebugElement;

  // Mock data
  const mockMembers: Person[] = [
    { id: 1, name: 'Member 1', gender: 0, french_knowledge: 5, old_dwwn: false, technical_knowledge: 5, profile: 0, age: 30 },
    { id: 2, name: 'Member 2', gender: 1, french_knowledge: 5, old_dwwn: false, technical_knowledge: 5, profile: 1, age: 30 },
    { id: 3, name: 'Member 3', gender: 2, french_knowledge: 5, old_dwwn: false, technical_knowledge: 5, profile: 2, age: 30 }
  ];

  const mockGroups: Group[] = [
    { id: 1, name: 'Group A', nbr_persons: 2, members: [mockMembers[0], mockMembers[1]] },
    { id: 2, name: 'Group B', nbr_persons: 2, members: [mockMembers[1], mockMembers[2]] }
  ];

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        GroupVisualizationComponent,
        NgForOf,
        RouterTestingModule, // Required for testing RouterLink
        MockDropdownSelectionComponent
      ]
    })
    .overrideComponent(GroupVisualizationComponent, {
      set: {
        imports: [
          NgForOf,
          RouterTestingModule,
          MockDropdownSelectionComponent
        ],
        template: `
          <h2 class="category-title">Groupes</h2>
          <div class="main-container">
            <button class="classic-button" [routerLink]="'/group-creation/1'">Créer un nouveau groupe</button>
            <app-dropdown-selection *ngFor="let group of groupsArrayWithNames" [title_text]="group.name"
                                  [optionTextsArray]="group.memberNames">
            </app-dropdown-selection>
          </div>
        `
      }
    })
    .compileComponents();

    fixture = TestBed.createComponent(GroupVisualizationComponent);
    component = fixture.componentInstance;
    debugElement = fixture.debugElement;

    // Set the mock data directly on the component
    component.groupsArray = mockGroups;

    fixture.detectChanges(); // This will call ngOnInit
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test ngOnInit transforms the data correctly
  it('should transform groups into groupsWithNames in ngOnInit', () => {
    // Verify groupsArrayWithNames has been populated
    expect(component.groupsArrayWithNames.length).toBe(2);

    // Verify the transformation includes memberNames arrays
    expect(component.groupsArrayWithNames[0].memberNames).toEqual(['Member 1', 'Member 2']);
    expect(component.groupsArrayWithNames[1].memberNames).toEqual(['Member 2', 'Member 3']);

    // Verify other properties were preserved
    expect(component.groupsArrayWithNames[0].id).toBe(1);
    expect(component.groupsArrayWithNames[0].name).toBe('Group A');
    expect(component.groupsArrayWithNames[1].id).toBe(2);
    expect(component.groupsArrayWithNames[1].name).toBe('Group B');
  });

  // Test the component renders the category title
  it('should display the category title', () => {
    const titleElement = debugElement.query(By.css('.category-title'));
    expect(titleElement).toBeTruthy();
    expect(titleElement.nativeElement.textContent).toContain('Groupes');
  });

  // Test dropdown components are rendered for each group
  it('should render dropdown selections for each group', () => {
    const dropdownElements = debugElement.queryAll(By.directive(MockDropdownSelectionComponent));

    // Check number of dropdowns
    expect(dropdownElements.length).toBe(2);

    // Check they have correct titles
    expect(dropdownElements[0].componentInstance.title_text).toBe('Group A');
    expect(dropdownElements[1].componentInstance.title_text).toBe('Group B');

    // Check they have correct member arrays
    expect(dropdownElements[0].componentInstance.optionTextsArray).toEqual(['Member 1', 'Member 2']);
    expect(dropdownElements[1].componentInstance.optionTextsArray).toEqual(['Member 2', 'Member 3']);
  });

  // Test component handles empty groups array
  it('should handle empty groups array', () => {
    // Set groups to empty array
    component.groupsArray = [];

    // Re-initialize component
    component.ngOnInit();
    fixture.detectChanges();

    // Check that groupsArrayWithNames is empty
    expect(component.groupsArrayWithNames.length).toBe(0);

    // Check that no dropdown elements are rendered
    const dropdownElements = debugElement.queryAll(By.directive(MockDropdownSelectionComponent));
    expect(dropdownElements.length).toBe(0);
  });

  // Test component layout structure
  it('should have the expected DOM structure', () => {
    // Check main container
    const mainContainer = debugElement.query(By.css('.main-container'));
    expect(mainContainer).toBeTruthy();

    // Check that the button is inside the main container
    const buttonInContainer = mainContainer.query(By.css('.classic-button'));
    expect(buttonInContainer).toBeTruthy();

    // Check that dropdowns are inside the main container
    const dropdownsInContainer = mainContainer.queryAll(By.directive(MockDropdownSelectionComponent));
    expect(dropdownsInContainer.length).toBe(2);
  });
});
