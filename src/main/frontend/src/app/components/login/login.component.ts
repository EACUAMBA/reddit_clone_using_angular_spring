import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {LoginRequestPayload} from "../../models/login/login-request.payload";
import {LocalStorageService} from "ngx-webstorage";
import {catchError, EMPTY} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy{
  loginRequestPayload : LoginRequestPayload;
  loginForm : FormGroup<any>;
  isError: boolean = false;
  registerSuccessfulMessage: string = '';

  constructor(private authenticationService : AuthenticationService,
              private localStorageService: LocalStorageService,
              private activatedRouter: ActivatedRoute,
              private toastr: ToastrService,
              private router: Router) {
    this.loginRequestPayload = {
      password: '',
      username: ''
    };
    this.loginForm = new FormGroup<any>({
      username: new FormControl('eacuamba', Validators.required),
      password: new FormControl('eacuamba.2022', Validators.required),
      }
    );
  }


  login = () => {
    this.loginRequestPayload = this.loginForm.getRawValue();
      this.authenticationService.login(this.loginRequestPayload)
        .pipe(catchError((error: HttpErrorResponse) => {
          this.toastr.error("Erro ao processar:\n" + error.message)
          this.isError = true;
          return EMPTY
        }))
        .subscribe({
          next: (response) => {
            this.localStorageService.store("authorization", response.headers.get("authorization"));
            this.localStorageService.store("refresh-token", response.headers.get("refresh-token"));
            this.isError = false;
            this.router.navigateByUrl('/')
            this.toastr.success('Login successful! Redirecting you to main page.')
          }
        });
  }

  ngOnDestroy(): void {
    this.activatedRouter.queryParams.subscribe().unsubscribe()
  }

  ngOnInit(): void {
    this.activatedRouter.queryParams
      .subscribe(params =>{
        if(params['registered']){
          this.toastr.success('Sign up successful');
          this.registerSuccessfulMessage = 'Please check your mail inbox, we sent you an link to activate your account!'
        }
      })
  }
}
