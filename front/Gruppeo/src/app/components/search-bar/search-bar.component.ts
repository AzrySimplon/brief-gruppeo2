import {Component, Output, EventEmitter} from '@angular/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-search-bar',
  imports: [FormsModule],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.css'
})
export class SearchBarComponent {
  inputText: string = "";
  @Output() outputSearch = new EventEmitter<string>();

  sendAnswer(){
    this.outputSearch.emit(this.inputText);
  }
}
