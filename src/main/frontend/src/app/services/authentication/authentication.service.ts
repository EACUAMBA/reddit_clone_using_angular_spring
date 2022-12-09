import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {SignupRequestPayload} from "../../models/signup-request.payload";
import {Observable} from "rxjs";
import {LoginRequestPayload} from "../../models/login/login-request.payload";
import {LocalStorageService} from "ngx-webstorage";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.httpClient.post(environment.apiBaseUrl.concat('auth/signup'), signupRequestPayload, {responseType: 'text'})
  }

  login = (loginRequestPayload: LoginRequestPayload) => {

    this.httpClient.post<any>(
      environment.apiBaseUrl.concat('auth/login'),
      loginRequestPayload,
      {observe: 'response', headers: new HttpHeaders()}
    )
      .subscribe({
        next: (response) => {
          this.localStorageService.store("authorization", response.headers.get("authorization"));
          this.localStorageService.store("refresh-token", response.headers.get("refresh-token"));
        },
        error: (error) => {
          throw error;
        },
        complete: () => {

        }
      });
  }
}
