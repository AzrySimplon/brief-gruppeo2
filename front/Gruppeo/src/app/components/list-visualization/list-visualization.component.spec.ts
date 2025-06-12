import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ListVisualizationComponent } from './list-visualization.component';
import { RouterTestingModule } from '@angular/router/testing';
import { CommonModule } from '@angular/common';
import { GlobalVariables } from '../../../globalVariables';

describe('ListVisualizationComponent', () => {
  let component: ListVisualizationComponent;
  let fixture: ComponentFixture<ListVisualizationComponent>;

  // Mock data to use for testing
  const mockLists: List[] = [
    {id: 1, name: 'List 1', nbr_persons: 0},
    {id: 2, name: 'List 2', nbr_persons: 0}
  ];

  // Store original lists value to restore later
  const originalLists = GlobalVariables.temporary.lists;

  beforeEach(async () => {
    // Set mock data for testing
    GlobalVariables.temporary.lists = mockLists as any;

    await TestBed.configureTestingModule({
      imports: [
        ListVisualizationComponent,
        CommonModule,
        RouterTestingModule // For RouterLink
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(ListVisualizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Restore the original value for cleanup
  afterAll(() => {
    GlobalVariables.temporary.lists = originalLists;
  });

  // Basic component creation test
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test the component initializes with lists from GlobalVariables
  it('should initialize listsArray from GlobalVariables', () => {
    expect(component.listsArray).toEqual(mockLists);
    expect(component.listsArray.length).toBe(2);
  });

  // Test that changes to GlobalVariables lists are reflected
  it('should reflect changes to GlobalVariables.temporary.lists', () => {
    // Arrange
    const updatedMockLists: List[] = [
      { id: 3, name: 'New List', nbr_persons: 0 }
    ];

    // Act
    GlobalVariables.temporary.lists = updatedMockLists as any;

    // Create new instance to test update
    const newFixture = TestBed.createComponent(ListVisualizationComponent);
    const newComponent = newFixture.componentInstance;
    newFixture.detectChanges();

    // Assert
    expect(newComponent.listsArray).toEqual(updatedMockLists);
    expect(newComponent.listsArray.length).toBe(1);
  });
});
