import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SignupRequestPayload} from "../../models/signup-request.payload";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signupRequestPayload: SignupRequestPayload;
  signupForm : FormGroup;

  constructor(private authenticationService: AuthenticationService ) {
    this.signupRequestPayload = {
      username: "",
      password: "",
      email: ""
    }

    this.signupForm = new FormGroup({
      username: new FormControl<string>('', Validators.required),
      email: new FormControl<string>('', [Validators.required, Validators.email]),
      password: new FormControl<string>('', Validators.required)
    });
  }

  ngOnInit(): void {

  }

  signup(){
    this.signupRequestPayload = this.signupForm.getRawValue();
    this.authenticationService.signup(this.signupRequestPayload)
      .subscribe(data => console.log(data))
  }
}
