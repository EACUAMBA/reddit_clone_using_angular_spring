import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {Observable} from "rxjs";
import {PostResponsePayload} from "../../models/post/post-response.payload";
import {PostRequest} from "./post";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) {
  }

  getAllPost(): Observable<Array<PostResponsePayload>> {
    return this.httpClient.get<Array<PostResponsePayload>>(environment.apiBaseUrl.concat('posts'))
  }

  save(postRequest: PostRequest) {
    return this.httpClient.post(environment.apiBaseUrl.concat('posts'), postRequest);
  }

  getPostById(postId: bigint) {
    return this.httpClient.get<PostResponsePayload>(
      environment.apiBaseUrl
        .concat('posts/')
        .concat(postId.toString()));
  }

  getAllPostByUser(username: string | undefined) {
    return this.httpClient.get<Array<PostResponsePayload>>(environment.apiBaseUrl.concat('posts/find-by-user-username/').concat(username as string));
  }
}
