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

  login = (loginRequestPayload: LoginRequestPayload) : Observable<any> => {
    return this.httpClient.post<any>(
      environment.apiBaseUrl.concat('auth/login'),
      loginRequestPayload,
      {observe: 'response', headers: new HttpHeaders()}
    );
  }

  getJWT(): string {
    return this.localStorageService.retrieve('authorization')
  }

  refreshJWT() : Observable<any>{
    return this.httpClient.post(environment.apiBaseUrl.concat('auth/refresh-token'), {refreshToken: this.localStorageService.retrieve('refresh-token')})
  }

  updateJWT(token: string) {
    this.localStorageService.store('authorization', '');
    this.localStorageService.store('authorization', token);
  }
}
