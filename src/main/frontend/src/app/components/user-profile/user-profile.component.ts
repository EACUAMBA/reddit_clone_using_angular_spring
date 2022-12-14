import {Component, OnInit} from '@angular/core';
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {CommentResponse} from "../../services/comment/comment";
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../services/post/post.service";
import {CommentsService} from "../../services/comment/comments.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  username: string | undefined;
  posts: PostResponsePayload[] = [];
  comments: CommentResponse[] = [];
  postLength: number = 0;
  commentLength: number = 0;

  constructor(
    private activatedRoute: ActivatedRoute,
    private postService: PostService,
    private commentsService: CommentsService
  ) {
    this.username = this.activatedRoute.snapshot.params['username'];

    this.postService.getAllPostByUser(this.username)
      .subscribe({
        next: (valuees) => {
          this.posts = valuees;
          this.postLength = this.posts.length
        }
      })
    this.commentsService.getALlByUsername(this.username)
      .subscribe({
        next: (valuees) => {
          this.comments = valuees;
          this.commentLength = this.comments.length
        }
      });
  }

  ngOnInit(): void {
  }

}
