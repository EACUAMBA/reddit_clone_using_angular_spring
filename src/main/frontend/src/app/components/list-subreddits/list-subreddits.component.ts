import {Component, OnInit} from '@angular/core';
import {SubredditResponse} from "../../services/subreddit/subredditResponse";
import {SubredditService} from "../../services/subreddit/subreddit.service";
import {ToastrService} from "ngx-toastr";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-list-subreddits',
  templateUrl: './list-subreddits.component.html',
  styleUrls: ['./list-subreddits.component.css']
})
export class ListSubredditsComponent implements OnInit {
  subreddits: SubredditResponse[] = [];

  constructor(private subredditService: SubredditService, private toastrService: ToastrService) {
  }

  ngOnInit(): void {
    this.subredditService.getAllSubreddit()
      .subscribe({
        next: (values) => {
          this.subreddits = values;
        },
        error: (err) => {
          let message = '';
          if (err instanceof HttpErrorResponse)
            message = err.message
          this.toastrService.error(message, 'Error getting all the subreddit!',);
        }
      });
  }


}
