import { Component } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {LoginRequestPayload} from "../../models/login/login-request.payload";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginRequestPayload : LoginRequestPayload;
  loginForm : FormGroup<any>;
  isError: boolean = false;

  constructor(private authenticationService : AuthenticationService) {
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
    console.log('processiong login')
    this.loginRequestPayload = this.loginForm.getRawValue();
    try {
      this.authenticationService.login(this.loginRequestPayload);
    }catch (e: any) {
      alert(e.error)
    }
  }
}
