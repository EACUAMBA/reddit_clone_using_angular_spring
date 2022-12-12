import {Component, OnInit} from '@angular/core';
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {PostService} from "../../services/post/post.service";
import {faArrowDown, faArrowUp, faComments} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  _posts: Array<PostResponsePayload> = [];
  faArrowUp: any = faArrowUp;
  upVoteColor: any = 'green';
  downVoteColor: any = 'red';
  faArrowDown: any = faArrowDown;
  faComments: any = faComments;

  constructor(private postService: PostService) {

  }

  goToPost(id: bigint) {

  }

  upvotePost() {

  }

  downvotePost() {

  }

  ngOnInit(): void {
    console.log("Testing ngOnInit")
    this.postService.getAllPost().subscribe({
      next: (data)=> this._posts = data
    })
  }
}
