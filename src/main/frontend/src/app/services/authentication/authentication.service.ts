import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {SignupRequestPayload} from "../../models/signup-request.payload";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {
  }

  signup(signupRequestPayload: SignupRequestPayload) : Observable<any> {
    return this.httpClient.post(environment.apiBaseUrl.concat('auth/signup'), signupRequestPayload, {responseType: 'text'})
  }
}
