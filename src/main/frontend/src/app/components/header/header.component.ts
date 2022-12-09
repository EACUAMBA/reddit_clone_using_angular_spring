import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isLoggedIn : boolean = false;
  username? : string = undefined;

  logout = () => {
    console.log("Want to logout?")
  }

  goToUserProfile = () => {
    console.log("Want to go to the user profile?")
  }
}
