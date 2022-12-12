import {Component, OnInit} from '@angular/core';
import {SubredditService} from "../../services/subreddit/subreddit.service";
import {Subreddit} from "../../services/subreddit/subreddit";

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {

  subreddits: Array<Subreddit> = [];
  displayViewAll: boolean = false;

  constructor(private subredditService: SubredditService) {
  }


  ngOnInit() {
    this.subredditService.getAllSubreddit()
      .subscribe((value) => {
        if (value.length >= 4) {
          this.subreddits = value.slice(0, 3);
          this.displayViewAll = false;
        } else {
          this.subreddits = value
        }
      });
  }
}
