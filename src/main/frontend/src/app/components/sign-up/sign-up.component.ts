import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SignupRequestPayload} from "../../models/signup-request.payload";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  signupRequestPayload: SignupRequestPayload;
  signupForm : FormGroup;

  constructor(private authenticationService: AuthenticationService, private router: Router, private toastr: ToastrService ) {
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

  signup(){
    this.signupRequestPayload = this.signupForm.getRawValue();
    this.authenticationService.signup(this.signupRequestPayload)
      .subscribe({
        next: () =>
        {
          this.router.navigate(['/login'], { queryParams: {registered: true}}).then()
        },
        error: (error) => {
          this.toastr.error("An error occurred during sign up." + error)
        }
      })
  }
}
