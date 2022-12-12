import {Component, OnInit} from '@angular/core';
import {SubredditResponse} from "../../services/subreddit/subredditResponse";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PostService} from "../../services/post/post.service";
import {PostRequest} from "../../services/post/post";
import {SubredditService} from "../../services/subreddit/subreddit.service";
import {ToastrService} from "ngx-toastr";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  subreddits: SubredditResponse[] = [];
  createPostForm: FormGroup;

  constructor(private postService: PostService, private subredditService: SubredditService, private toastrService: ToastrService, private router: Router) {
    this.createPostForm = new FormGroup<any>({
      name: new FormControl('', Validators.required),
      url: new FormControl(null),
      description: new FormControl(null),
      subredditId: new FormControl(null, Validators.required),
    })
  }

  ngOnInit(): void {
    this.subredditService.getAllSubreddit().subscribe({
      next: (data) => {
        this.subreddits = data
      }
    })
  }

  discardPost() {
    this.router.navigateByUrl('/')
  }

  createPost() {

    let postRequest: PostRequest = this.createPostForm.getRawValue();

    this.postService.save(postRequest)
      .subscribe({
        next: (data) => {
          let message: string = "Post saved!"
          this.toastrService.success(message, 'Post saved');
          this.createPostForm.reset();
        },
        error: (err) => {
          let message = '';
          if (err instanceof HttpErrorResponse)
            message = err.message
          this.toastrService.error(message, 'Error saving the subreddit!');
        }
      });
  }
}
