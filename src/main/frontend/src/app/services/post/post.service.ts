import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {Observable} from "rxjs";
import {PostResponsePayload} from "../../models/post/post-response.payload";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  getAllPost(): Observable<Array<PostResponsePayload>>{
    return this.httpClient.get<Array<PostResponsePayload>>(environment.apiBaseUrl.concat('posts'))
  }
}
