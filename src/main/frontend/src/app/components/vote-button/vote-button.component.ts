import {Component, Input} from '@angular/core';
import {faArrowDown, faArrowUp, faComments} from '@fortawesome/free-solid-svg-icons';
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {VoteService} from "../../services/vote/vote.service";
import {VoteRequest, VoteType} from "../../services/vote/vote";
import {PostService} from "../../services/post/post.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent {
  faArrowUp: any = faArrowUp;
  upVoteColor: any = '';
  downVoteColor: any = '';
  faArrowDown: any = faArrowDown;
  faComments: any = faComments;

  @Input() post: PostResponsePayload | undefined;

  constructor(private voteService: VoteService, private postService: PostService, private toastrService: ToastrService) {
  }

  upvotePost() {
    let voteRequest: VoteRequest = {voteType: VoteType[VoteType.UP], postId: this.post?.id as bigint};
    this.voteService.vote(voteRequest)
      .subscribe({
        next: (response) => {
          if (response.status == 200) {
            this.updatePost()
            this.updatePost();
          }
        },
        error: (err) => {
          if (err instanceof HttpErrorResponse) {
            this.showError(err)
          }
        }
      });
  }

  downvotePost() {
    let voteRequest: VoteRequest = {voteType: VoteType[VoteType.DOWN], postId: this.post?.id as bigint};
    this.voteService.vote(voteRequest)
      .subscribe({
        next: (response) => {
          if (response.status == 200) {
            this.updatePost()
          }
        },
        error: (err) => {
          if (err instanceof HttpErrorResponse) {
            this.showError(err)
          }
        }
      });
  }

  showError(error: HttpErrorResponse) {
    this.toastrService.error(error.error.title, 'Error when vote!')
  }

  updatePost() {
    this.postService.getById(this.post?.id as bigint)
      .subscribe({
        next: (post) => {
          this.post = post;
        }
      })
  }

}
