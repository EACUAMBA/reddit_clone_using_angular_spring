import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {CreatePostComponent} from "./components/create-post/create-post.component";
import {CreateSubredditComponent} from "./components/create-subreddit/create-subreddit.component";
import {ListSubredditsComponent} from "./components/list-subreddits/list-subreddits.component";
import {ViewPostComponent} from "./components/view-post/view-post.component";
import {UserProfileComponent} from "./components/user-profile/user-profile.component";
import {AuthenticationGuard} from "./authentication/authentication.guard";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'sign-up',
    component: SignUpComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'create-post',
    component: CreatePostComponent,
    canActivate: [AuthenticationGuard]
  },
  {
    path: 'create-subreddit',
    component: CreateSubredditComponent,
    canActivate: [AuthenticationGuard]
  },
  {
    path: 'list-subreddit',
    component: ListSubredditsComponent
  },
  {
    path: 'view-post/:id',
    component: ViewPostComponent
  },
  {
    path: 'user-profile/:username',
    component: UserProfileComponent,
    canActivate: [AuthenticationGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
