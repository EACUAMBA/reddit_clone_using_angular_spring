import {Component, OnInit} from '@angular/core';
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {faArrowDown, faArrowUp, faComments} from "@fortawesome/free-solid-svg-icons";
import {PostService} from "../../services/post/post.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  _posts: Array<PostResponsePayload> = [];
  faArrowUp: any = faArrowUp;
  upVoteColor: any = 'green';
  downVoteColor: any = 'red';
  faArrowDown: any = faArrowDown;
  faComments: any = faComments;


  constructor(private postService: PostService, private router: Router) {
  }

  ngOnInit(): void {
    this.postService.getAllPost().subscribe({
      next: (data) => this._posts = data
    })
  }
}
