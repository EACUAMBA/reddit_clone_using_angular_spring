import {Component, OnInit} from '@angular/core';
import {PostResponsePayload} from "../../models/post/post-response.payload";
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




  ngOnInit(): void {
  }
}
