import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment.dev";
import {VoteRequest} from "./vote";

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private httpClient: HttpClient) {
  }

  vote = (vote: VoteRequest): Observable<HttpResponse<any>> => {
    return this.httpClient.post(environment.apiBaseUrl.concat('votes'), vote, {observe: 'response'});
  }
}
