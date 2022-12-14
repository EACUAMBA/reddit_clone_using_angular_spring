import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PostService} from "../../services/post/post.service";
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CommentResponse, CommentResquest} from "../../services/comment/comment";
import {CommentsService} from "../../services/comment/comments.service";

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  post: PostResponsePayload | undefined;
  commentForm: FormGroup;
  comments: CommentResponse[] = [];

  constructor(
    private postService: PostService,
    private activatedRoute: ActivatedRoute,
    private commentsService: CommentsService
  ) {
    let postId: bigint = this.activatedRoute.snapshot.params['id'];
    this.postService.getPostById(postId).subscribe({
      next: (post) => {
        this.post = post;
        this.getComments()
      },
      error: (err) => {
        console.log(err)
      }
    });

    this.commentForm = new FormGroup<any>({
      text: new FormControl('', Validators.required)
    });
  }


  ngOnInit(): void {

  }


  postComment() {
    let comment: CommentResquest = {text: '', postId: this.post?.id as bigint};
    comment.text = this.commentForm.get('text')?.value

    this.commentsService.postComment(comment)
      .subscribe({
        next: (data) => {
          this.commentForm.reset();
          this.getComments();
        },
        error: (error) => {

        }
      });

  }

  getComments() {
    this.commentsService.getAllByPost(this.post?.id as bigint)
      .subscribe({
        next: (comments) => {
          this.comments = comments;
        }
      });
  }

}
