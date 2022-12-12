import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignUpComponent} from "./components/sign-up/sign-up.component";
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {CreatePostComponent} from "./components/create-post/create-post.component";
import {CreateSubredditComponent} from "./components/create-subreddit/create-subreddit.component";
import {ListSubredditsComponent} from "./components/list-subreddits/list-subreddits.component";

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
    component: CreatePostComponent
  },
  {
    path: 'create-subreddit',
    component: CreateSubredditComponent
  },
  {
    path: 'list-subreddit',
    component: ListSubredditsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
