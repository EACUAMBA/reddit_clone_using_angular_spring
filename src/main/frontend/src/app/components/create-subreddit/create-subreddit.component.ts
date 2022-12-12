import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SubredditRequest} from "../../services/subreddit/subredditResponse";
import {SubredditService} from "../../services/subreddit/subreddit.service";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-create-subreddit',
  templateUrl: './create-subreddit.component.html',
  styleUrls: ['./create-subreddit.component.css']
})
export class CreateSubredditComponent {
  createSubredditForm: FormGroup;

  constructor(private subredditService: SubredditService, private toastrService: ToastrService, private router: Router) {
    this.createSubredditForm = new FormGroup<any>({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    })
  }

  save() {
    let subreddit: SubredditRequest = this.createSubredditForm.getRawValue();
    this.subredditService.save(subreddit)
      .subscribe({
        next: () => {
          this.toastrService.success('Subreddit saved successfully!');
          this.router.navigateByUrl('/list-subreddit');
        },
        error: (err) => {
          let message = '';
          if (err instanceof HttpErrorResponse)
            message = err.message
          this.toastrService.error(message, 'Error saving the subreddit!',);
        }
      })
  }

  createSubreddit() {

  }

  discard() {
    this.router.navigateByUrl('/');
  }
}
