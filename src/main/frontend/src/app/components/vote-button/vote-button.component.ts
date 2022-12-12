import {Component, Input} from '@angular/core';
import {faArrowDown, faArrowUp, faComments} from '@fortawesome/free-solid-svg-icons';
import {PostResponsePayload} from "../../models/post/post-response.payload";

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent {
  faArrowUp: any = faArrowUp;
  upVoteColor: any = 'green';
  downVoteColor: any = 'red';
  faArrowDown: any = faArrowDown;
  faComments: any = faComments;

  @Input() post: PostResponsePayload | undefined;

  upvotePost() {

  }

  downvotePost() {

  }

}
