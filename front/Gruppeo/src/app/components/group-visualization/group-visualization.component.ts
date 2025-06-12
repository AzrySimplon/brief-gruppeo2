import {Component, Input, OnInit} from '@angular/core';
import {NgForOf} from '@angular/common';
import {GlobalVariables} from '../../../globalVariables';
import {DropdownSelectionComponent} from '../dropdown-selection/dropdown-selection.component';
import {RouterLink} from '@angular/router';

interface GroupWithNames extends Group {
  memberNames: string[];
}

@Component({
  selector: 'app-group-visualization',
  imports: [
    NgForOf, DropdownSelectionComponent, RouterLink
  ],
  templateUrl: './group-visualization.component.html',
  styleUrl: './group-visualization.component.css'
})
export class GroupVisualizationComponent implements OnInit {
  @Input() groupsArray: Group[] = GlobalVariables.temporary.groups;
  groupsArrayWithNames: GroupWithNames[] = [];

  ngOnInit() {
    this.groupsArrayWithNames = this.groupsArray.map(group => ({
      ...group,
      memberNames: group.members.map(member => member.name)
    }));
  }
}
