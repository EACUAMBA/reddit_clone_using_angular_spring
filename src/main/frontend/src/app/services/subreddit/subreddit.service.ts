import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SubredditRequest, SubredditResponse} from "./subredditResponse";
import {environment} from "../../../environments/environment.dev";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SubredditService {

  constructor(private http: HttpClient) {
  }

  getAllSubreddit(): Observable<Array<SubredditResponse>> {
    return this.http.get<Array<SubredditResponse>>(environment.apiBaseUrl.concat('subreddits'));
  }

  save(subreddit: SubredditRequest) {
    return this.http.post(environment.apiBaseUrl.concat('subreddits'), subreddit);
  }
}
