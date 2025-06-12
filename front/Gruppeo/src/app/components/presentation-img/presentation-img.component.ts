import { Component, Input } from '@angular/core';
import {NgOptimizedImage, NgIf} from '@angular/common';

@Component({
  selector: 'app-presentation-img',
  imports: [
    NgOptimizedImage,
    NgIf
  ],
  templateUrl: './presentation-img.component.html',
  styleUrl: './presentation-img.component.css'
})
export class PresentationImgComponent {
  @Input() img!: string;
  @Input() text!: string;
  @Input() text_alt!: string;
}
