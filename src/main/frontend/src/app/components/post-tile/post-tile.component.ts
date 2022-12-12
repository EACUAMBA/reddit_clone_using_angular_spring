import {Component, OnInit} from '@angular/core';
import {PostService} from "../../services/post/post.service";
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {faArrowDown, faArrowUp, faCircleDot, faComments, faDotCircle} from '@fortawesome/free-solid-svg-icons';
import {} from '@fortawesome/fontawesome-svg-core';
import {} from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit{
  _posts: Array<PostResponsePayload> = [];
  faComments: any = faComments;
  faDotCircle: any = faCircleDot;

  constructor(private postService: PostService) {

  }

  goToPost(id: bigint) {

  }



  ngOnInit(): void {
    console.log("Testing ngOnInit")
    this.postService.getAllPost().subscribe({
      next: (data)=> this._posts = data
    })
  }

}
