import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isLoggedIn: boolean = false;
  username: string | undefined;

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {

  }

  logout = () => {
    console.log("Want to logout?")
  }

  ngOnInit(): void {
    this.isLoggedIn = this.authenticationService.isLoggedIn();
    this.username = this.authenticationService.getUsername() as string;
  }

  goToUserProfile() {
    this.router.navigateByUrl('/user-profile/' + this.username)
  }
}
