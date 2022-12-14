import {Component, Input} from '@angular/core';
import {CommentResponse} from "../../services/comment/comment";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent {
  @Input() _comment: CommentResponse | undefined;

}
