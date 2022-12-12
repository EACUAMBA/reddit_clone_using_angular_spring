import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BehaviorSubject, catchError, EMPTY, Observable, switchMap, throwError} from 'rxjs';
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  isRefreshingToken: boolean = false;
  refreshingTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private authenticationService : AuthenticationService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const jwt = this.authenticationService.getJWT();
    if(jwt){
      request = this.addTokenInRequest(request, jwt);
    }

    if(request.url.indexOf('/refresh-token') || request.url.indexOf('/login')){
      next.handle(request);
    }

    return next.handle(request).pipe(catchError(err => {
      if(err instanceof HttpErrorResponse && err.status === 403){
          return this.handleAuthError(
            request, next
          )
      }
      else{
        return throwError(err)
      }

    }))
  }

  addTokenInRequest = (request: HttpRequest<any>, jwt: string): HttpRequest<unknown> => {
    let httpHeaders = request.headers.set('Authorization', jwt);
    return request.clone({headers: httpHeaders});
  }

  handleAuthError(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>>{
    if(!this.isRefreshingToken){
      this.isRefreshingToken = true;
      this.refreshingTokenSubject.next(null);

      return this.authenticationService.refreshJWT()
        .pipe(switchMap((refreshTokenResponse: {token: string})=>{
          this.isRefreshingToken = false;
          this.refreshingTokenSubject.next(refreshTokenResponse.token);
          this.authenticationService.updateJWT(refreshTokenResponse.token);
          return next.handle(this.addTokenInRequest(request, refreshTokenResponse.token))
        }))
    }
    return EMPTY;
  }

}
