import {Component, inject, Input} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GlobalVariables} from "../../../globalVariables";
import {RouterLink} from '@angular/router';
import {ListService} from '../../services/list/list.service';
import {ListInterface} from '../../interface/list-interface/list-interface';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-list-interface-visualization',
  imports: [CommonModule, RouterLink],
  templateUrl: './list-visualization.component.html',
  styleUrl: './list-visualization.component.css'
})

export class ListVisualizationComponent {
  listService: ListService = inject(ListService);
  listArray: Observable<ListInterface[]> = this.listService.getListByUserId();

  ngOnInit() {
  }
}
