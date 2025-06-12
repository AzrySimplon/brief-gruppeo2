import { Component, Input } from '@angular/core';
import {CommonModule} from '@angular/common';
import {GlobalVariables} from "../../../globalVariables";
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-list-visualization',
  imports: [CommonModule, RouterLink],
  templateUrl: './list-visualization.component.html',
  styleUrl: './list-visualization.component.css'
})

export class ListVisualizationComponent {
  listsArray : List[] = GlobalVariables.temporary.lists;
}
