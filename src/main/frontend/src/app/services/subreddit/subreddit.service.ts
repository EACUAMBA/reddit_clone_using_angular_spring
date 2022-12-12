import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Subreddit} from "./subreddit";
import {environment} from "../../../environments/environment.dev";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SubredditService {

  constructor(private http: HttpClient) {
  }

  getAllSubreddit(): Observable<Array<Subreddit>> {
    return this.http.get<Array<Subreddit>>(environment.apiBaseUrl.concat('subreddits'));
  }
}
