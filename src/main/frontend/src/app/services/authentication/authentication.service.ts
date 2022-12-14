import {EventEmitter, Injectable, Output} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment.dev";
import {SignupRequestPayload} from "../../models/signup-request.payload";
import {map, Observable} from "rxjs";
import {LoginRequestPayload} from "../../models/login/login-request.payload";
import {LocalStorageService} from "ngx-webstorage";
import jwtDecode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() username: EventEmitter<string> = new EventEmitter<string>();

  constructor(
    private httpClient: HttpClient,
    private localStorageService: LocalStorageService
  ) {

    if (this.isLoggedIn()) {
      this.loggedIn.emit(true);
      this.username.emit(this.getUsername());
    }
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.httpClient.post(environment.apiBaseUrl.concat('auth/signup'), signupRequestPayload, {responseType: 'text'})
  }

  login = (loginRequestPayload: LoginRequestPayload) : Observable<any> => {
    return this.httpClient.post<any>(
      environment.apiBaseUrl.concat('auth/login'),
      loginRequestPayload,
      {observe: 'response', headers: new HttpHeaders()}
    ).pipe(map(data => {
      this.localStorageService.store("authorization", data.headers.get("authorization"));
      this.localStorageService.store("refresh-token", data.headers.get("refresh-token"));

      this.loggedIn.emit(true);
      this.username.emit(this.getUsername());
      return true;
    }));
  }

  getJWT(): string {
    return this.localStorageService.retrieve('authorization')
  }

  refreshJWT(): Observable<any> {
    return this.httpClient.post(environment.apiBaseUrl.concat('auth/refresh-token'), {refreshToken: this.localStorageService.retrieve('refresh-token')})
  }

  updateJWT(token: string) {
    this.localStorageService.store('authorization', '');
    this.localStorageService.store('authorization', token);
  }

  isLoggedIn(): boolean {
    return this.getJWT() != null;
  }

  getUsername(): string {
    jwtDecode(this.getJWT())
    let uncodedJwt: any = jwtDecode(this.getJWT());
    return uncodedJwt.sub as string;
  }

  logout() {
    this.httpClient.post(environment.apiBaseUrl.concat('auth/logout/'), {refreshToken: this.localStorageService.retrieve('refresh-token')})
      .subscribe({
        next: (data) => {
          console.log(data)
        },
        error: (err) => {
          throw err;
        }
      });

    this.localStorageService.clear('authorization');
    this.localStorageService.clear('refresh-token');

    this.loggedIn.emit(false);
    this.username.emit('');
  }
}
