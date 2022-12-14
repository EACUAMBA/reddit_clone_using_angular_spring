import {Component, Input, OnInit} from '@angular/core';
import {PostService} from "../../services/post/post.service";
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {faCircleDot, faComments} from '@fortawesome/free-solid-svg-icons';
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {
  @Input() _posts: Array<PostResponsePayload> = [];
  faComments: any = faComments;
  faDotCircle: any = faCircleDot;

  constructor(private postService: PostService, private router: Router) {

  }

  goToPost(id: bigint) {
    this.router.navigateByUrl('/view-post/' + id)
  }



  ngOnInit(): void {
  }

}
