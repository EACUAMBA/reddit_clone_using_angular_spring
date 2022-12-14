import {Injectable} from '@angular/core';
import {CommentResponse, CommentResquest} from "./comment";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentsService {

  constructor(private http: HttpClient) {
  }

  postComment(comment: CommentResquest) {
    return this.http.post(environment.apiBaseUrl.concat('comments'), comment);
  }

  getAllByPost(id: bigint): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(environment.apiBaseUrl.concat('comments/by-post-id/').concat(id.toString()))
  }

  getALlByUsername(username: string | undefined) {
    return this.http.get<CommentResponse[]>(environment.apiBaseUrl.concat('comments/by-user-username/').concat(username as string));
  }
}
